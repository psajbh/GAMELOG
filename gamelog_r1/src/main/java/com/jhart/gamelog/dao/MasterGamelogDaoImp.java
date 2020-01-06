package com.jhart.gamelog.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jhart.gamelog.api.dao.MasterGameLogDao;
import com.jhart.gamelog.api.model.IModel;
import com.jhart.gamelog.entities.gamelog.EntityGameLineBat;
import com.jhart.gamelog.entities.gamelog.EntityGameLineDefense;
import com.jhart.gamelog.entities.gamelog.EntityGameLinePitch;
import com.jhart.gamelog.entities.gamelog.EntityGamelog;
import com.jhart.gamelog.entities.gamelog.EntityGamelogAwards;
import com.jhart.gamelog.entities.gamelog.EntityLineItemOrder;
import com.jhart.gamelog.entities.gamelog.EntityLineItemPos;
import com.jhart.gamelog.entities.gamelog.EntityTeamGamelog;
import com.jhart.gamelog.entities.gamelog.EntityUmpireGamelog;
import com.jhart.gamelog.entities.park.EntityPark;
import com.jhart.gamelog.entities.person.EntityPerson;
import com.jhart.gamelog.entities.team.EntitySeason;
import com.jhart.gamelog.entities.team.EntityTeam;
import com.jhart.gamelog.exception.DbExecutionException;
import com.jhart.gamelog.model.Gamelog;
import com.jhart.gamelog.transform.GamelogTransformer;
import com.jhart.gamelog.utils.GamelogDuplicateManager;
import com.jhart.gamelog.utils.dbconfig.exception.NullSessionException;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class MasterGamelogDaoImp extends GameLogDao implements MasterGameLogDao {
    private static final Logger LOG = LoggerFactory.getLogger(MasterGamelogDaoImp.class);
    
    private GamelogTransformer transformer = new GamelogTransformer();
    private GamelogDuplicateManager duplicateManager = new GamelogDuplicateManager();
    
    private PersonDao personDao = new PersonDao();
    private TeamDao teamDao = new TeamDao();
    private ParkDao parkDao = new ParkDao();
    private SeasonDao seasonDao = new SeasonDao();
    private Integer recordLimiter = null;
    
    @Override
    public List<IModel> getAll() throws NullSessionException, DbExecutionException{
    	List<IModel> gamelogs = new ArrayList<>();

        List<EntityGamelog> entityGamelogs = getAllEntityGamelogs();
        LOG.debug("GamelogDaoImpl:getAll-1  - translate existing entity gamelogs to a parsed gamelog");
        if (entityGamelogs.size() > 0) {
            for(EntityGamelog dp: entityGamelogs) {
                Gamelog gamelog = (Gamelog)transformer.transform(dp);
                gamelogs.add((IModel)gamelog);
            }
        }
        LOG.debug("GamelogDaoImpl:getAll-2 - returning list of Gamelog objects that are persisted: " + gamelogs.size());
        return gamelogs;
    }
    
    @Override
    public List<EntityGamelog> getPersistedGamelogs(){
        LOG.debug("getPersistedGameLogs [1] - start");
        List<EntityGamelog> persistedGameLogs = new ArrayList<>();
        try {
        	LOG.debug("getPersistedGameLogs [2] - calling getAllEntityGamelogs");
            persistedGameLogs = getAllEntityGamelogs();
        }
        catch(Exception e) {
            LOG.error("getPersistedGameLogs: [3]  - error: " + e.getMessage());
        }
        LOG.debug("getPersistedGameLogs: [4] - finish - number of records: " + persistedGameLogs.size());
        return persistedGameLogs;        
    }
    
    /**
     * saveAll method that supports constraining the number of records persisted.
     * this supports testing smaller samplings of data.
     */
    @Override
    public void saveAll(List<Gamelog> modelGamelog, Integer i) throws NullSessionException, DbExecutionException, Exception{
        if (null != i) {
            recordLimiter = i;
        }
        else {
        	recordLimiter = null;
        }
        saveAll(modelGamelog);
    }
    
    @Override
    public void saveAll(List<Gamelog> modelGamelog) throws NullSessionException, DbExecutionException, Exception{
        LOG.debug("saveAll: start - modelTeamSeason size: " + modelGamelog.size());
        int count = 0;
        Session session = getSession();
        
        try {
            EntitySeason entitySeason = seasonDao.getEntitySeason(session, modelGamelog.get(0).getSeason());
            
            for(Gamelog gamelog : modelGamelog) {
                count++;
                LOG.debug("saveAll: [2]--processing gamelog: " + count + " starting transformation");
                EntityGamelog entityGamelog = (EntityGamelog)transformer.transform(gamelog);
                if (null != entityGamelog) {
                    EntityGamelog newGameLog = null;
                    try {
                    	LOG.info("saveAll: [3] -- saving gamelog: " + count);
                        newGameLog = saveGamelog(entityGamelog,session, entitySeason);
                        LOG.trace("saveAll: [4] -- finished saveGamelog  - count: " + count);
                    
                        newGameLog = saveAwards(newGameLog,entityGamelog.getEntityGamelogAwards(),session);
                        LOG.trace("saveAll: [5] - finished save gamelog_awards - count: " + count);
                    
                        newGameLog = saveUmpireLogs(newGameLog, entityGamelog.getUmpires(), session);
                        LOG.trace("saveAll: [6] - finished save umpires_gamelog - count: " + count);
                    }
                    catch(Exception e) {
                        LOG.error("saveAll: [7] - failed to commit basicGamelog - msg: " + e.getMessage());
                        //session.getTransaction().rollback();
                        throw new Exception("failed to persist basic gamelog msg: " + e.getMessage(), e);
                    }
                        
                    try {
                        LOG.debug("saveAll: [8 - processing home team - count: " + count);
                        processTeamData(newGameLog, entityGamelog, session, true);
                    }
                    catch(Exception e) {
                        LOG.debug("saveAll: [9]  - exception msg: " + e.getMessage());
                    }
                    
                    try {
                        LOG.debug("saveAll: [10] - processing visiting team - count: " + count);
                        processTeamData(newGameLog, entityGamelog, session, false);
                    }
                    catch(Exception e) {
                        //session.getTransaction().commit();
                        LOG.debug("saveAll: [12] - exception msg: " + e.getMessage());
                    }
                }
                else {
                    LOG.error("saveAll: [13] failed to transform gamelog - entity gamelog null");
                }
                
                if (null != recordLimiter && count > recordLimiter) {
                	LOG.debug("saveAll: count exceeds recordLimiter - breaking out of translation loop");
                    break;
                }
            }
            
            session.close();
        }
        catch(Exception e) {
            LOG.error("saveAll: [14] exception: " + e.getMessage(),e);
        }
        finally {
            
            if(null != session) {
                session.close();
                LOG.debug("saveAll: [15] - closing session");
            }
        }
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked"})
    private List<EntityGamelog> getAllEntityGamelogs() throws NullSessionException, DbExecutionException{
        LOG.debug("getAllEntityGamelogs: [1] - query for all persisted entity gamelogs");
        List<EntityGamelog> entityGamelogs;
        
        Session session = getSession();
        session.beginTransaction();
        Query query= session.createQuery("from EntityGamelog");
        entityGamelogs = query.list();
        session.getTransaction().commit();
        session.close();
        LOG.debug("getAllEntityGamelogs: [2] - query completed - number of records: " + entityGamelogs.size());
        return entityGamelogs;
    }
    
    
	private EntityGamelog saveGamelog(EntityGamelog gamelog, Session session, EntitySeason season) throws NullSessionException, DbExecutionException{
        LOG.trace("saveGamelog: [1] - gamelog: " + gamelog.getGameCode());
        EntityGamelog entityGamelog = new EntityGamelog();
        
        try {
        	session.beginTransaction();
        	entityGamelog.setSeason(season);
        	EntityPark entityPark = duplicateManager.getDuplicatePark(gamelog.getEntityPark().getCode());
        	if (null == entityPark) {
        		entityPark = parkDao.getEntityPark(session, gamelog.getEntityPark().getCode());
        		duplicateManager.setEntityPark(entityPark);
        	}
        	
        	entityGamelog.setEntityPark(entityPark);
            entityGamelog.setGameCode(gamelog.getGameCode());
            entityGamelog.setAttendance(gamelog.getAttendance());
            entityGamelog.setDateOfGame(gamelog.getDateOfGame());
            entityGamelog.setDayOrNight(gamelog.getDayOrNight());
            
            entityGamelog.setGameType(gamelog.getGameType());
            entityGamelog.setWeekday(gamelog.getWeekday());
            entityGamelog.setTimeOfGame(gamelog.getTimeOfGame());
            
            entityGamelog.setHomeScoreLine(gamelog.getHomeScoreLine());
            entityGamelog.setVisitorScoreLine(gamelog.getVisitorScoreLine());
            entityGamelog.setHomeTeamScore(gamelog.getHomeTeamScore());
            entityGamelog.setVisitorTeamScore(gamelog.getVisitorTeamScore());
            entityGamelog.setTotalOuts(gamelog.getTotalOuts());
            
            try {
                session.saveOrUpdate(entityGamelog);
                LOG.debug("saveGamelog: [2] - successfully saved gamelog id: " + entityGamelog.getGamelogId());
                session.getTransaction().commit();
            }
            catch(Exception e) {
            	LOG.debug("saveGamelog: [3] - error executing save entityGamelog msg: " + e.getMessage());
            	session.getTransaction().rollback();
            }
        }
        catch(Exception sqle) {
            LOG.error("saveGamelog: [4] - save error - " + sqle.getMessage(), sqle);
            throw new DbExecutionException(sqle);
        }
        return entityGamelog;
    }
    
    private void processTeamData(EntityGamelog newGameLog, EntityGamelog entityGamelog, Session session, boolean home) throws Exception{
    	LOG.error("processTeamData:  [1] - start");
    	
    	if (home) {
    		newGameLog = saveTeam(newGameLog, entityGamelog.getHomeTeam(),session, true);
    		EntityTeamGamelog newHomeTeam = newGameLog.getHomeTeam();
            newHomeTeam = saveGameLineBat(newHomeTeam, entityGamelog.getHomeTeam().getGameLineBat(),session);
            newHomeTeam = saveGameLinePitch(newHomeTeam, entityGamelog.getHomeTeam().getGameLinePitch(),session);
            newHomeTeam = saveGameLineDefense(newHomeTeam, entityGamelog.getHomeTeam().getGameLineDefense(),session);
            newHomeTeam = saveLineItemPos(newHomeTeam, entityGamelog.getHomeTeam().getLineItemPos(),session);
            newHomeTeam = saveLineItemOrder(newHomeTeam, entityGamelog.getHomeTeam().getLineItemOrder(),session);
    	}
    	else {
    		newGameLog = saveTeam(newGameLog, entityGamelog.getVisitTeam(),session, false);
            EntityTeamGamelog newVisitTeam = newGameLog.getVisitTeam();
            newVisitTeam = saveGameLineBat(newVisitTeam, entityGamelog.getVisitTeam().getGameLineBat(),session);
            newVisitTeam = saveGameLinePitch(newVisitTeam, entityGamelog.getVisitTeam().getGameLinePitch(),session);
            newVisitTeam = saveGameLineDefense(newVisitTeam, entityGamelog.getVisitTeam().getGameLineDefense(),session);
            newVisitTeam = saveLineItemPos(newVisitTeam, entityGamelog.getVisitTeam().getLineItemPos(),session);
            newVisitTeam = saveLineItemOrder(newVisitTeam, entityGamelog.getVisitTeam().getLineItemOrder(),session);
    	}
    	LOG.error("processTeamData:  [2] - finished");
    }
    
    
    private EntityGamelog saveUmpireLogs(EntityGamelog newGameLog, EntityUmpireGamelog umpireLog, Session session) throws NullSessionException, Exception {
    	LOG.debug("saveUmpireLogs:  [1] - start");
    	try {
    		session.beginTransaction();
    		EntityUmpireGamelog newEntityUmpireGamelog = new EntityUmpireGamelog();
    		newEntityUmpireGamelog.setHomePlateUmpire(getPerson(umpireLog.getHomePlateUmpire().getCode()));
    		newEntityUmpireGamelog.setFirstBaseUmpire(getPerson(umpireLog.getFirstBaseUmpire().getCode()));
    		newEntityUmpireGamelog.setSecondBaseUmpire(getPerson(umpireLog.getSecondBaseUmpire().getCode()));
    		newEntityUmpireGamelog.setThirdBaseUmpire(getPerson(umpireLog.getThirdBaseUmpire().getCode()));
    		newEntityUmpireGamelog.setLeftFieldUmpire(getPerson(umpireLog.getLeftFieldUmpire().getCode()));
    		newEntityUmpireGamelog.setRightFieldUmpire(getPerson(umpireLog.getRightFieldUmpire().getCode()));
    		
    		newEntityUmpireGamelog.setGameCode(newGameLog.getGameCode());
    		newEntityUmpireGamelog.setGameLog(newGameLog);
    		newGameLog.setUmpires(newEntityUmpireGamelog);
    		
            session.saveOrUpdate(newGameLog);
            session.getTransaction().commit();
    	}
    	catch(Exception e) {
            LOG.debug("saveUmpireLogs: [2] - error executing save saveUmpireLogs msg: " + e.getMessage());
            session.getTransaction().rollback();
            throw new Exception(e);
    	}
    	
    	LOG.debug("saveUmpireLogs:  [3] - finished");
    	return newGameLog;
    	
    }
    
    private EntityGamelog saveAwards(EntityGamelog newGameLog, EntityGamelogAwards entityGamelogAwards,Session session) throws NullSessionException, Exception{
    	LOG.debug("saveAwards:  [1] - start");
    	try {
    		session.beginTransaction();
    		EntityGamelogAwards newEntityGamelogAwards = new EntityGamelogAwards();
    		newEntityGamelogAwards.setGwrbi(getPerson(entityGamelogAwards.getGwrbi().getCode()));
    		newEntityGamelogAwards.setWinningPitcher(getPerson(entityGamelogAwards.getWinningPitcher().getCode()));
    		newEntityGamelogAwards.setLosingPitcher(getPerson(entityGamelogAwards.getLosingPitcher().getCode()));
    		newEntityGamelogAwards.setSave(getPerson(entityGamelogAwards.getSave().getCode()));
    		
    		newEntityGamelogAwards.setGameCode(newGameLog.getGameCode());
    		newEntityGamelogAwards.setGamelog(newGameLog);
    		newGameLog.setEntityGamelogAwards(newEntityGamelogAwards);
            session.saveOrUpdate(newGameLog);
            session.getTransaction().commit();
    	}
    	catch(Exception e) {
            LOG.debug("saveAwards:  [2] - error executing save saveAwards msg: " + e.getMessage());
            //ERROR o.h.i.ExceptionMapperStandardImpl - HHH000346: Error during managed flush [A different object with the same identifier value was already associated with the session : [com.jhart.gamelog.entities.person.EntityPerson#13978]]
            session.getTransaction().rollback();
            throw new Exception(e);
    	}
    	LOG.debug("saveAwards:  [3] - finished");
        return newGameLog;
    }
        
    private EntityTeamGamelog saveLineItemOrder(EntityTeamGamelog newTeamGameLog, EntityLineItemOrder modelLineItemOrder, Session session)throws NullSessionException, Exception {
    	LOG.debug("saveLineItemOrder:  [1] - start");    
        try {
        	session.beginTransaction();
            EntityLineItemOrder entityLineItemOrder = new EntityLineItemOrder();
            entityLineItemOrder.setGameCode(newTeamGameLog.getGameCode());
            entityLineItemOrder.setTmGamelog(newTeamGameLog);
            
            entityLineItemOrder.setOrder1Player(getPerson(modelLineItemOrder.getOrder1Player().getCode()));
            entityLineItemOrder.setOrder2Player(getPerson(modelLineItemOrder.getOrder2Player().getCode()));
            entityLineItemOrder.setOrder3Player(getPerson(modelLineItemOrder.getOrder3Player().getCode()));
            entityLineItemOrder.setOrder4Player(getPerson(modelLineItemOrder.getOrder4Player().getCode()));
            entityLineItemOrder.setOrder5Player(getPerson(modelLineItemOrder.getOrder5Player().getCode()));
            entityLineItemOrder.setOrder6Player(getPerson(modelLineItemOrder.getOrder6Player().getCode()));
            entityLineItemOrder.setOrder7Player(getPerson(modelLineItemOrder.getOrder7Player().getCode()));
            entityLineItemOrder.setOrder8Player(getPerson(modelLineItemOrder.getOrder8Player().getCode()));
            entityLineItemOrder.setOrder9Player(getPerson(modelLineItemOrder.getOrder9Player().getCode()));

            newTeamGameLog.setLineItemOrder(entityLineItemOrder);
            session.saveOrUpdate(newTeamGameLog);
            session.getTransaction().commit();
        }
        catch(Exception e) {
            LOG.debug("saveLineItemOrder: [2] - error executing save saveLineItemOrder msg: " + e.getMessage());
            //ERROR o.h.i.ExceptionMapperStandardImpl - HHH000346: Error during managed flush [A different object with the same identifier value was already associated with the session : [com.jhart.gamelog.entities.person.EntityPerson#13978]]
            session.getTransaction().rollback();
            throw new Exception(e);
        }
        LOG.debug("saveLineItemOrder:  [3] - finished");
        return newTeamGameLog;
        
    }
    
    private EntityTeamGamelog saveLineItemPos(EntityTeamGamelog newTeamGameLog, EntityLineItemPos modelLineItemPos, Session session)throws NullSessionException, Exception {
    	LOG.debug("saveLineItemPos:  [1] - start");
        try {
        	session.beginTransaction();
            EntityLineItemPos entityLineItemPos = new EntityLineItemPos();
            entityLineItemPos.setGameCode(newTeamGameLog.getGameCode());
            entityLineItemPos.setTmGamelog(newTeamGameLog);
            newTeamGameLog.setLineItemPos(entityLineItemPos);
            
            entityLineItemPos.setPitcher(getPerson(modelLineItemPos.getPitcher().getCode()));
            entityLineItemPos.setCatcher(getPerson(modelLineItemPos.getCatcher().getCode()));
            entityLineItemPos.setFirstbase(getPerson(modelLineItemPos.getFirstbase().getCode()));
            entityLineItemPos.setSecondbase(getPerson(modelLineItemPos.getSecondbase().getCode()));
            entityLineItemPos.setThirdbase(getPerson(modelLineItemPos.getThirdbase().getCode()));
            entityLineItemPos.setShortstop(getPerson(modelLineItemPos.getShortstop().getCode()));
            entityLineItemPos.setLeftfield(getPerson(modelLineItemPos.getLeftfield().getCode()));
            entityLineItemPos.setCenterfield(getPerson(modelLineItemPos.getCenterfield().getCode()));
            entityLineItemPos.setRightfield(getPerson(modelLineItemPos.getRightfield().getCode()));
            entityLineItemPos.setDesignatedhitter(getPerson(modelLineItemPos.getDesignatedhitter().getCode()));
            entityLineItemPos.setManager(getPerson(modelLineItemPos.getManager().getCode()));
            
            session.saveOrUpdate(newTeamGameLog);
            session.getTransaction().commit();
            
        }
        catch(Exception e) {
            LOG.debug("saveLineItemPos:  [2] - error executing save saveLineItemPos msg: " + e.getMessage());
            session.getTransaction().rollback();
            throw new Exception(e);
        }
        LOG.debug("saveLineItemPos:  [3] - finish");
        return newTeamGameLog;
    }
    
    private EntityTeamGamelog saveGameLineDefense(EntityTeamGamelog newTeamGameLog, EntityGameLineDefense entityGameLineDefense, Session session)throws NullSessionException, Exception {
    	LOG.debug("saveGameLineDefense:  [1] - start");
        try {
        	session.beginTransaction();
            entityGameLineDefense.setGameCode(newTeamGameLog.getGameCode());
            entityGameLineDefense.setTmGamelog(newTeamGameLog);
            newTeamGameLog.setGameLineDefense(entityGameLineDefense);
            
            session.saveOrUpdate(newTeamGameLog);
            session.getTransaction().commit();
            
        }
        catch(Exception e) {
            LOG.debug("saveGameLineDefense: [2] - error executing save entityGameLineDefense msg: " + e.getMessage());
            session.getTransaction().rollback();
            throw new Exception(e);
        }
        LOG.debug("saveGameLineDefense:  [3] - finish");
        return newTeamGameLog;
    }
    
    
    private EntityTeamGamelog saveGameLinePitch(EntityTeamGamelog newTeamGameLog, EntityGameLinePitch entityGameLinePitch, Session session) throws NullSessionException, Exception {
    	LOG.debug("saveGameLinePitch:  [1] - start");
        
        try {
        	session.beginTransaction();
            entityGameLinePitch.setGameCode(newTeamGameLog.getGameCode());
            entityGameLinePitch.setTmGamelog(newTeamGameLog);
            newTeamGameLog.setGameLinePitch(entityGameLinePitch);
            
            session.saveOrUpdate(newTeamGameLog);
            session.getTransaction().commit();
            
        }
        catch(Exception e) {
            LOG.debug("saveGameLinePitch: [2] - error executing save entityGameLinePitch msg: " + e.getMessage());
            session.getTransaction().rollback();
            throw new Exception(e);
        }
        LOG.debug("saveGameLinePitch:  [3] - finish");
        return newTeamGameLog;
    }
    
    
    private EntityTeamGamelog saveGameLineBat(EntityTeamGamelog newTeamGameLog, EntityGameLineBat entityGameLineBat, Session session) throws NullSessionException, Exception {
    	LOG.debug("saveGameLineBat:  [1] - start");
        try {
        	session.beginTransaction();
            entityGameLineBat.setGameCode(newTeamGameLog.getGameCode());
            entityGameLineBat.setTmGamelog(newTeamGameLog);
            newTeamGameLog.setGameLineBat(entityGameLineBat);
            session.saveOrUpdate(newTeamGameLog);
            session.getTransaction().commit();
            
        }
        catch(Exception e) {
            LOG.debug("saveGameLineBat: [2] - error executing save entityGameLineBat msg: " + e.getMessage());
            session.getTransaction().rollback();
            throw new Exception(e);
        }
    	LOG.debug("saveGameLineBat:  [3] - finish");
        return newTeamGameLog;
    }
    
    
    private EntityGamelog saveTeam(EntityGamelog newGameLog, EntityTeamGamelog modelTeamGameLog, Session session, boolean homeTeam) throws NullSessionException, Exception{
    	LOG.debug("saveTeam:  [1] - start");
        EntityTeamGamelog newEntityTeamGamelog = new EntityTeamGamelog();
        EntityTeam team = modelTeamGameLog.getTeam();
        
        try {
        	session.beginTransaction();
            EntityTeam newTeam = teamDao.getEntityTeam(session, team.getCode(), newGameLog.getSeason());
        
            if (null != newTeam) {
                newEntityTeamGamelog.setTeam(newTeam);
                newEntityTeamGamelog.setGameCode(newGameLog.getGameCode());
                newEntityTeamGamelog.setGameLog(newGameLog);
                newEntityTeamGamelog.setLocationType(modelTeamGameLog.getLocationType());
                if (homeTeam) {
                    newGameLog.setHomeTeam(newEntityTeamGamelog);
                }
                else {
                    newGameLog.setVisitTeam(newEntityTeamGamelog);
                }
                session.saveOrUpdate(newGameLog);
                session.getTransaction().commit();
            }
            else {
            	
            	LOG.warn(newGameLog.getSeason().getCode() +" " + team.getCode() + " is NOT persisted");
            }
        }
        catch(Exception e) {
        	LOG.debug("saveTeam:  [2] - exception " + e.getMessage());
        	session.getTransaction().rollback();
            throw new Exception(e.getMessage());
        }
        LOG.debug("saveTeam:  [3] - finish");
        return newGameLog;
    }
    

    private EntityPerson getPerson(String code) throws NullSessionException{
    	LOG.debug("getPerson: [1] - start code: " + code);
    	if (null == code) {
    		LOG.debug("getPerson: [2] - code is null - returning");
    		return null;
    	}
    	
    	EntityPerson person = null;
		person = duplicateManager.getDuplicatePerson(code);
		LOG.debug("getPerson: [3] - person generated from duplicate manager");
		if (null == person) {
			person = personDao.getEntityPerson(code);
			if (null != person) {
				duplicateManager.setEntityPerson(person);
			}
			else {
				LOG.debug("getPerson: - [4] - code value not included in person table, code: " + code);
			}
		}

		LOG.debug("getPerson: - [5] code value not included in person table, code: " + code);
    	return person;
    }

}
