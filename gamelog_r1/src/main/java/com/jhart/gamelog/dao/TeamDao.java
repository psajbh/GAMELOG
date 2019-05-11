package com.jhart.gamelog.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jhart.gamelog.api.model.IModel;
import com.jhart.gamelog.entities.location.EntityLocation;
import com.jhart.gamelog.entities.team.EntityDivision;
import com.jhart.gamelog.entities.team.EntityFranchise;
import com.jhart.gamelog.entities.team.EntityLeague;
import com.jhart.gamelog.entities.team.EntitySeason;
import com.jhart.gamelog.entities.team.EntityTeam;
import com.jhart.gamelog.exception.DbExecutionException;
import com.jhart.gamelog.exception.GamelogException;
import com.jhart.gamelog.model.team.Team;
import com.jhart.gamelog.model.team.TeamSeason;
import com.jhart.gamelog.transform.TeamTransformer;
import com.jhart.gamelog.utils.dbconfig.exception.NullSessionException;

public class TeamDao extends GameLogDao{
    private static final Logger LOG = LoggerFactory.getLogger(TeamDao.class);
    private TeamTransformer transformer = new TeamTransformer();
    
    public TeamDao() {}
 
    @Override
    public List<IModel> getAll() throws NullSessionException, DbExecutionException{
        LOG.debug("getAll() - get all team entities and match against parsed model");
        List<IModel> teams = new ArrayList<>();
        
        List<EntityTeam> entityTeams = getAllEntityTeams();
        LOG.debug("getAll() - translate existing entity teams to a parsed team");
        if (entityTeams.size() > 0) {
            for(EntityTeam dp: entityTeams) {
                // need to test transformer.
                Team team = (Team)transformer.transform(dp);
                
                teams.add((IModel)team);
            }
        }
        LOG.debug("returning list of Park objects that are persisted: " + teams.size());
        return teams;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private List<EntityTeam> getAllEntityTeams() throws NullSessionException, DbExecutionException{
        LOG.debug("TeamDao : getAllEntityTeams - query for all persisted entity teams");
        List<EntityTeam> entityTeams;
        
        Session session = getSession();
        session.beginTransaction();
        Query query= session.createQuery("from EntityTeam");
        entityTeams = query.list();
        session.getTransaction().commit();
        session.close();
        LOG.debug("TeamDao : getAllEntityTeams - query completed - number of records: " + entityTeams.size());
        return entityTeams;
    }
    
    public List<EntityTeam> getPersistedTeams() {
    	LOG.debug("TeamDao:getPersistedTeams - start");
        List<EntityTeam> persistedTeams = new ArrayList<>();
        try {
            persistedTeams = getAllEntityTeams();
        }
        catch(Exception e) {
            LOG.error("getPersistedTeams: " + e.getMessage());
        }
        LOG.debug("TeamDAO : getPersistedTeams - finish - number of records: " + persistedTeams.size());
        return persistedTeams;
    }

    // TODO: consider making this an interface method
    public void saveAll(List<TeamSeason> modelTeamSeason) throws NullSessionException, DbExecutionException{
    	// transaction start here.
    	LOG.debug("TeamDao : saveAll - modelTeamSeason size: " + modelTeamSeason.size());
    	int i = 0;
    	Session session = getSession();
    	session.beginTransaction();
    	try {
    		for(TeamSeason teamSeason : modelTeamSeason) {
    		    LOG.trace("processing team number:" + i++ );
    			EntityTeam entityTeam = (EntityTeam)transformer.transform(teamSeason);
    			save(entityTeam,session);
    		}
    		session.getTransaction().commit();
    		session.close();
    	}
    	catch(Exception e) {
    	    LOG.error("TeamDao:saveAll - rolling back changes msg: " + e.getMessage());
    		session.getTransaction().rollback();
    		
    	}
    	finally {
    		if(null != session) {
    			session.close();
    		}
    	}
    }    
    
	private void save(EntityTeam entityTeam, Session session) throws NullSessionException, DbExecutionException{
    	
        try {
        	LOG.trace("TeamDao:save - processing season");
        	SeasonDao seasonDao = new SeasonDao();
        	EntitySeason entitySeason = seasonDao.getEntitySeason(session, entityTeam.getSeason().getCode());
        	entityTeam.setSeason(entitySeason);
        	
        	LOG.trace("TeamDao:save - processing location");
        	LocationDao locationDao = new LocationDao();
        	EntityLocation entityLocation = locationDao.getEntityLocation(session, entityTeam.getLocation().getStateCode(),entityTeam.getLocation().getCity());
        	entityTeam.setLocation(entityLocation);
        	
            LOG.trace("TeamDao:save - processing franchise");
            FranchiseDao franchiseDao = new FranchiseDao();
            EntityFranchise entityFranchise = franchiseDao.getEntityFranchise(session, entityTeam.getFranchise().getCode());
            entityTeam.setFranchise(entityFranchise);
            
            LOG.trace("processing league/division");
            LeagueDao leagueDao = new LeagueDao();
            EntityLeague league = entityTeam.getLeague();
            EntityDivision division = league.getDivision();
            EntityLeague entityLeague = null;
            
            if (null != division) {
                entityLeague = leagueDao.getEntityLeagueWithDivision(session, league.getCode(), division.getCode());
            }
            else {
                entityLeague = leagueDao.getEntityLeagueWithOutDivision(session, league.getCode());
            }
            
            entityTeam.setLeague(entityLeague);
            
            session.saveOrUpdate(entityTeam);
            LOG.debug("TeamDao:save - saveOrUpdtate: team code: " + entityTeam.getCode() + " season: " + entityTeam.getSeason().getCode());
        }
        catch(Exception sqle) {
            LOG.error("save error - " + sqle.getMessage(), sqle);
            throw new DbExecutionException(sqle);
        }
    }
	
	
//	public EntityTeam getEntityTeam(String code, EntitySeason season) throws NullSessionException{
//		Session session = getSession();
//		EntityTeam entityTeam = getEntityTeam(session, code, season);
//		session.close();
//		return entityTeam;
//	}
    
	@SuppressWarnings("unchecked")
	public EntityTeam getEntityTeam(Session session, String code, EntitySeason season) throws GamelogException, NullSessionException{
    	
    	if (StringUtils.isBlank(code) || StringUtils.isBlank(season.getCode())) {
    		return null;
    	}
		try {
			Query<EntityTeam> qTeam = session.getNamedQuery("get_team_from_code_and_season");
			qTeam.setParameter("code", code);
			qTeam.setParameter("season", season);
			
			EntityTeam entityTeam = (EntityTeam)qTeam.uniqueResult();
			return entityTeam;
		}
		catch(RuntimeException re) {
			LOG.error("TeamDao:getEntityTeam - exception msg: " + re.getMessage());
			throw new GamelogException(re);
		}
	}

}
