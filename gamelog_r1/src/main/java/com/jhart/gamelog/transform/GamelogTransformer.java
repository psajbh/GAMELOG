package com.jhart.gamelog.transform;

import java.time.LocalDate;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jhart.gamelog.api.GameLine;
import com.jhart.gamelog.api.entities.IEntity;
import com.jhart.gamelog.api.model.IModel;
import com.jhart.gamelog.api.transform.Transformer;
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
import com.jhart.gamelog.model.Gamelog;
import com.jhart.gamelog.model.Person;
import com.jhart.gamelog.model.Player;
import com.jhart.gamelog.model.TeamGameLog;
import com.jhart.gamelog.model.Umpire;
import com.jhart.gamelog.model.UmpireGameLog;
import com.jhart.gamelog.model.gameline.BattingGameLine;
import com.jhart.gamelog.model.gameline.DefenseGameLine;
import com.jhart.gamelog.model.gameline.PitchingGameLine;
import com.jhart.gamelog.model.team.HomeTeam;
import com.jhart.gamelog.model.team.VisitingTeam;
import com.jhart.gamelog.service.GamelogService;
import com.jhart.gamelog.types.GameLineType;
import com.jhart.gamelog.types.LocationType;
import com.jhart.gamelog.types.StartingBattingOrder;
import com.jhart.gamelog.types.StartingPosition;
import com.jhart.gamelog.types.UmpirePosition;
import com.jhart.gamelog.utils.Utils;

public class GamelogTransformer implements Transformer{
    private static final Logger LOG = LoggerFactory.getLogger(GamelogTransformer.class);
    
    public IEntity transform(IModel gamelog) {
        LOG.debug("GamelogTransformer:transform - start");
    	Gamelog gamelogModel  = (Gamelog)gamelog; 
    	
        EntityGamelog entityGamelog = new EntityGamelog();
        
        String season = gamelogModel.getSeason();
        int id = gamelogModel.getGameLogId();
        
        entityGamelog.setGameCode(Utils.getGamelogCode(season, id));
        
        EntitySeason entitySeason = new EntitySeason();
        entitySeason.setCode(season);
        entityGamelog.setSeason(entitySeason);
      
        LocalDate date = Utils.getFormattedLocalDate(gamelogModel.getStrDate(), Utils.YRMODA_SANFORMAT);
        entityGamelog.setDateOfGame(date);
        
        entityGamelog.setGameType(gamelogModel.getGameType());
        entityGamelog.setWeekday(gamelogModel.getWeekDay());
        entityGamelog.setDayOrNight(gamelogModel.getDayOrNight());
        entityGamelog.setTotalOuts(gamelogModel.getGameTotalOuts());
        entityGamelog.setAttendance(gamelogModel.getAttendance());
        entityGamelog.setTimeOfGame(gamelogModel.getTimeOfGame());
        
        EntityPark entityPark = new EntityPark();
        entityPark.setCode(gamelogModel.getParkId());
        entityGamelog.setEntityPark(entityPark);
        
        // process Awards
        EntityGamelogAwards entityGamelogAwards = new EntityGamelogAwards();
        entityGamelog.setEntityGamelogAwards(entityGamelogAwards);
        EntityPerson entityPerson = null;
        
        Player player = gamelogModel.getWinningPitcher();
        entityPerson = getEntityPerson(player);
        entityGamelogAwards.setWinningPitcher(entityPerson);

        player = gamelogModel.getLosingPitcher();
        entityPerson = getEntityPerson(player);
        entityGamelogAwards.setLosingPitcher(entityPerson);
        
        player = gamelogModel.getSavingPitcher();
        entityPerson = getEntityPerson(player);
        entityGamelogAwards.setSave(entityPerson);

        player = gamelogModel.getGwRbiPlayer();
        entityPerson = getEntityPerson(player);
        entityGamelogAwards.setGwrbi(entityPerson);
        
        //---------- umpire processing ------------//
        EntityUmpireGamelog entityUmpireGamelog = new EntityUmpireGamelog();
        UmpireGameLog umpireGamelog = gamelogModel.getUmpireGameLog();
        entityUmpireGamelog.setGameCode(Utils.getGamelogCode(season, umpireGamelog.getGameLogId()));
        entityUmpireGamelog.setGameLog(entityGamelog);
        setupUmpirePositions(entityUmpireGamelog, umpireGamelog);
        entityGamelog.setUmpires(entityUmpireGamelog);
        

        //-------home team processing --------//
        HomeTeam homeTeam = gamelogModel.getHomeTeam();
        EntityTeam entityHomeTeam = new EntityTeam();
        entityHomeTeam.setSeason(entitySeason);
        entityHomeTeam.setCode(homeTeam.getName());
      //-------home team gamelog processing --------//
        EntityTeamGamelog entityHomeTeamGamelog = new EntityTeamGamelog();
        gamelogModel.getHomeTeamGameNumber();
        entityHomeTeamGamelog.setGameCode(Utils.getGamelogCode(season, gamelogModel.getHomeTeamGameNumber()));  
        entityHomeTeamGamelog.setTeam(entityHomeTeam);
        entityHomeTeamGamelog.setLocationType(LocationType.H);
        entityHomeTeamGamelog.setGameLog(entityGamelog);
        entityGamelog.setHomeTeam(entityHomeTeamGamelog);
      //-------visiting team processing --------//
        VisitingTeam visitingTeam = gamelogModel.getVisitingTeam();
        EntityTeam entityVisitingTeam = new EntityTeam();
        entityVisitingTeam.setSeason(entitySeason);
        entityVisitingTeam.setCode(visitingTeam.getName());
      //-------visiting team gamelog processing --------//
        EntityTeamGamelog entityVisitingTeamGamelog = new EntityTeamGamelog();
        entityVisitingTeamGamelog.setGameCode(Utils.getGamelogCode(season, gamelogModel.getVisitingTeamGameNumber()));
        entityVisitingTeamGamelog.setTeam(entityVisitingTeam);
        entityVisitingTeamGamelog.setLocationType(LocationType.V);
        entityVisitingTeamGamelog.setGameLog(entityGamelog);
        entityGamelog.setVisitTeam(entityVisitingTeamGamelog);
        //------------gamelog opponenents score -----------//
        entityGamelog.setHomeScoreLine(gamelogModel.getHomeTeamGameScore());
        entityGamelog.setHomeTeamScore(gamelogModel.getHomeTeamScore());
        entityGamelog.setVisitorScoreLine(gamelogModel.getVisitingTeamGameScore());
        entityGamelog.setVisitorTeamScore(gamelogModel.getVisitingTeamScore());
        
        //---------- process game details  -----------------//
        
        for (Map.Entry<LocationType, TeamGameLog> entry : gamelogModel.getTeamLogMap().entrySet()) {
            
        	if (entry.getKey() == LocationType.V) {
        		System.out.println("work visiting team gamelog: " + entityVisitingTeamGamelog);
        		TeamGameLog teamGameLogVisitor = entry.getValue();
        		
                processBattingOrder(teamGameLogVisitor.getLineup().getStartingOrder(), entityVisitingTeamGamelog);
                processStartingPositions(teamGameLogVisitor.getLineup().getStartingPositions(),entityVisitingTeamGamelog);
                
                for (Map.Entry<GameLineType,GameLine> gameLineEntry : teamGameLogVisitor.getGamelog().getGameLines().entrySet()) {
                    GameLineType gameLineType = gameLineEntry.getKey();
                    if (gameLineType == GameLineType.VB) {
                        BattingGameLine battingGameLine = (BattingGameLine)gameLineEntry.getValue();
                        processBattingGameLine(battingGameLine, entityVisitingTeamGamelog);
                    }
                    else if (gameLineType == GameLineType.VD){
                        DefenseGameLine defenseGameLine = (DefenseGameLine)gameLineEntry.getValue();
                        processDefenseGameLine(defenseGameLine,entityVisitingTeamGamelog);
                    }
                    else if (gameLineType == GameLineType.VP){
                        PitchingGameLine pitchingGameLine = (PitchingGameLine)gameLineEntry.getValue();
                        processPitchingGameLine(pitchingGameLine, entityVisitingTeamGamelog);
                    }
                }
        	}
        	else {
        		System.out.println("work home team gamelog " + entityHomeTeamGamelog);
        		TeamGameLog teamGameLogHome = entry.getValue();
        		processBattingOrder(teamGameLogHome.getLineup().getStartingOrder(), entityHomeTeamGamelog);
        		processStartingPositions(teamGameLogHome.getLineup().getStartingPositions(),entityHomeTeamGamelog);
        		
        		for (Map.Entry<GameLineType,GameLine> gameLineEntry : teamGameLogHome.getGamelog().getGameLines().entrySet()) {
        		    GameLineType gameLineType = gameLineEntry.getKey();
        		    if (gameLineType == GameLineType.HB) {
        		        BattingGameLine battingGameLine = (BattingGameLine)gameLineEntry.getValue();
        		        processBattingGameLine(battingGameLine, entityHomeTeamGamelog);
        		    }
        		    else if (gameLineType == GameLineType.HD){
        		        DefenseGameLine defenseGameLine = (DefenseGameLine)gameLineEntry.getValue();
        		        processDefenseGameLine(defenseGameLine,entityHomeTeamGamelog);
        		    }
                    else if (gameLineType == GameLineType.HP){
                        PitchingGameLine pitchingGameLine = (PitchingGameLine)gameLineEntry.getValue();
                        processPitchingGameLine(pitchingGameLine, entityHomeTeamGamelog);
                    }
        		}
        	}
            
        }
        
        System.out.println("entityGamelog: " + entityGamelog);
        return entityGamelog;
        

    }
    
    private void processPitchingGameLine(PitchingGameLine pitchingGameLine, EntityTeamGamelog entityTeamGamelog) {
        EntityGameLinePitch entityGameLinePitch = new EntityGameLinePitch();
        entityTeamGamelog.setGameLinePitch(entityGameLinePitch);
        entityGameLinePitch.setGameCode(entityTeamGamelog.getGameCode());
        entityGameLinePitch.setBalks(pitchingGameLine.getBalks());
        entityGameLinePitch.setIndividualEr(pitchingGameLine.getIndividualEr());
        entityGameLinePitch.setPitchersUsed(pitchingGameLine.getPitchersUsed());
        entityGameLinePitch.setTeamEr(pitchingGameLine.getTeamEr());
        entityGameLinePitch.setWp(pitchingGameLine.getWp());
    }
    
    private void processDefenseGameLine(DefenseGameLine defenseGameLine, EntityTeamGamelog entityTeamGamelog) {
    	EntityGameLineDefense entityGameLineDefense = new EntityGameLineDefense();
    	entityTeamGamelog.setGameLineDefense(entityGameLineDefense);
    	entityGameLineDefense.setAssists(defenseGameLine.getAssists());
    	entityGameLineDefense.setDp(defenseGameLine.getDp());
    	entityGameLineDefense.setErrors(defenseGameLine.getErrors());
    	entityGameLineDefense.setGameCode(entityTeamGamelog.getGameCode());
    	entityGameLineDefense.setPb(defenseGameLine.getPb());
    	entityGameLineDefense.setPutouts(defenseGameLine.getPutouts());
    	entityGameLineDefense.setTp(defenseGameLine.getTp());
        // process defense game line.
    }
    
    private void processBattingGameLine(BattingGameLine battingGameLine, EntityTeamGamelog entityTeamGamelog) {
    	EntityGameLineBat entityGameLineBat = new EntityGameLineBat();
    	entityTeamGamelog.setGameLineBat(entityGameLineBat);
    	entityGameLineBat.setAb(battingGameLine.getAb());
    	entityGameLineBat.setAwci(battingGameLine.getAwci());
    	entityGameLineBat.setBb(battingGameLine.getBb());
    	entityGameLineBat.setCs(battingGameLine.getCs());
    	entityGameLineBat.setD(battingGameLine.getD());
    	entityGameLineBat.setGameCode(entityTeamGamelog.getGameCode());
    	entityGameLineBat.setGidp(battingGameLine.getGidp());
    	entityGameLineBat.setH(battingGameLine.getH());
    	entityGameLineBat.setHbp(battingGameLine.getHbp());
    	entityGameLineBat.setHr(battingGameLine.getHr());
    	entityGameLineBat.setIbb(battingGameLine.getIbb());
    	entityGameLineBat.setLob(battingGameLine.getLob());
    	entityGameLineBat.setRbi(battingGameLine.getRbi());
    	entityGameLineBat.setSb(battingGameLine.getSb());
    	entityGameLineBat.setSf(battingGameLine.getSf());
    	entityGameLineBat.setSh(battingGameLine.getSh());
    	entityGameLineBat.setSo(battingGameLine.getSh());
    	entityGameLineBat.setT(battingGameLine.getT());
    }
    
    private void processStartingPositions(Map<StartingPosition, Player> positionLineUp, EntityTeamGamelog entityTeamGamelog) {
    	EntityLineItemPos entityLineItemPos = new EntityLineItemPos();
    	entityTeamGamelog.setLineItemPos(entityLineItemPos);
    	entityLineItemPos.setGameCode(entityTeamGamelog.getGameCode());
    	boolean processDH = false;
    	
    	for (Map.Entry<StartingPosition,Player> gameLineEntry : positionLineUp.entrySet()) {
    		StartingPosition startingPosition = gameLineEntry.getKey();
    		if (startingPosition == StartingPosition.DH) {
    		    processDH = true;
    		}
    		processStartingPos(startingPosition, gameLineEntry.getValue(), entityLineItemPos);
    	}
    	
    	if (!processDH) {
    	    Player player = new Player("","");
    	    processStartingPos(StartingPosition.DH, player, entityLineItemPos);
    	}
    }
    
    private void processStartingPos(StartingPosition startingPosition, Person person, EntityLineItemPos entityLineItemPos) {
    
    	switch (startingPosition) {
    		case M:
    			entityLineItemPos.setManager(getEntityPerson(person));
    			break;
    		case P:
    			entityLineItemPos.setPitcher(getEntityPerson(person));
    			break;
    		case C:
    			entityLineItemPos.setCatcher(getEntityPerson(person));
    			break;
    		case FB:
    			entityLineItemPos.setFirstbase(getEntityPerson(person));
    			break;
    		case SB:
    			entityLineItemPos.setSecondbase(getEntityPerson(person));
    			break;
    		case TB:
    			entityLineItemPos.setThirdbase(getEntityPerson(person));
    			break;
    		case SS:
    			entityLineItemPos.setShortstop(getEntityPerson(person));
    			break;
    		case LF:
    			entityLineItemPos.setLeftfield(getEntityPerson(person));
    			break;
    		case CF:
    			entityLineItemPos.setCenterfield(getEntityPerson(person));
    			break;
    		case RF:
    			entityLineItemPos.setRightfield(getEntityPerson(person));
    			break;
            case DH:
                entityLineItemPos.setDesignatedhitter(getEntityPerson(person));
                break;
    	}
    }
    
    private void processBattingOrder(Map<StartingBattingOrder, Player> battingLineUpOrder, EntityTeamGamelog entityTeamGamelog) {
    	EntityLineItemOrder entityLineItemOrder = new EntityLineItemOrder();
    	entityTeamGamelog.setLineItemOrder(entityLineItemOrder);
    	entityLineItemOrder.setGameCode(entityTeamGamelog.getGameCode());
    	
    	for (Map.Entry<StartingBattingOrder,Player> gameLineEntry : battingLineUpOrder.entrySet()) {
    		StartingBattingOrder startingBattingOrder = gameLineEntry.getKey();
    		processBattingOrder(startingBattingOrder, gameLineEntry.getValue(), entityLineItemOrder);
    	}
    }

    private void processBattingOrder(StartingBattingOrder startingBattingOrder, Person person, EntityLineItemOrder entityLineItemOrder) {
    	
    	switch (startingBattingOrder) {
    		case FIRST:
    			entityLineItemOrder.setOrder1Player(getEntityPerson(person));
    			break;
    		case SECOND:
    			entityLineItemOrder.setOrder2Player(getEntityPerson(person));
    			break;
    		case THIRD:
    			entityLineItemOrder.setOrder3Player(getEntityPerson(person));
    			break;
    		case FOURTH:
    			entityLineItemOrder.setOrder4Player(getEntityPerson(person));
    			break;
    		case FIFTH:
    			entityLineItemOrder.setOrder5Player(getEntityPerson(person));
    			break;
    		case SIXTH:
    			entityLineItemOrder.setOrder6Player(getEntityPerson(person));
    			break;
    		case SEVENTH:
    			entityLineItemOrder.setOrder7Player(getEntityPerson(person));
    			break;
    		case EIGHTH:
    			entityLineItemOrder.setOrder8Player(getEntityPerson(person));
    			break;
    		case NINTH:
    			entityLineItemOrder.setOrder9Player(getEntityPerson(person));
    			break;
    	}
    }
    
    
    public IModel transform (IEntity entityGamelog){
        
        return null;
    }
    
    /**
     *         String season = gamelogModel.getSeason();
        int id = gamelogModel.getGameLogId();
        entityGamelog.setGamelogCode(createGamelogCode(season, id))
        String codeId = String.valueOf(id);
        int codeLength = codeId.length();
        if (codeLength == 1) {
        	String prefix = "000";
        }
        i
        
        
        String code = season + "-" + id;

     */
    
//    private String getGamelogCode(String season, int id) {
//    	String padding = getPaddingZeros(id);
//    	return season + "-" + padding;
//    }
//    
//    private String getPaddingZeros(int idCode) {
//    	
//    	String codeId = String.valueOf(idCode);
//    	String idPad = null;
//    	
//    	switch(codeId.length()) {
//    	
//    		case 1:
//    			idPad = "000";
//    			break;
//    		
//    		case 2:
//    			idPad = "00";
//    			break;
//    			
//    		case 3:
//    			idPad = "0";
//    			break;
//    			
//    		default :
//    			idPad = "";
//    	}
//    	
//    	return idPad + codeId;
//    }
    
    
    private void setupUmpirePositions(EntityUmpireGamelog entityUmpireGamelog, UmpireGameLog umpireGamelog) {
        boolean[] processUmps = new boolean[] {false, false, false, false, false, false}; 
        char[]  umps = new char[] {'H', 'F', 'S', 'T', 'L', 'R'};
        
        for (Map.Entry<UmpirePosition, Umpire> entry : umpireGamelog.getGameUmps().entrySet()) {
            UmpirePosition position = entry.getKey();
            
            switch (position) {
            
                case HP:
                    processUmps[0] = true;
                    entityUmpireGamelog.setHomePlateUmpire(getEntityPerson(entry.getValue()));
                    break;
                case FB:
                    processUmps[1] = true;
                    entityUmpireGamelog.setFirstBaseUmpire(getEntityPerson(entry.getValue()));
                    break;
                case SB:
                    processUmps[2] = true;
                    entityUmpireGamelog.setSecondBaseUmpire(getEntityPerson(entry.getValue()));
                    break;
                case TB:
                    processUmps[3] = true;
                    entityUmpireGamelog.setThirdBaseUmpire(getEntityPerson(entry.getValue()));
                    break;
                case LF:
                    processUmps[4] = true;
                    entityUmpireGamelog.setLeftFieldUmpire(getEntityPerson(entry.getValue()));
                    break;
                case RF:
                    processUmps[5] = true;
                    entityUmpireGamelog.setRightFieldUmpire(getEntityPerson(entry.getValue()));
                    break;
                default:
                    break;
            }
        }
        
        int count = 0;
        for (boolean b : processUmps) {
            if (!b) {
                char c = umps[count];
                processNullUmp(c, entityUmpireGamelog);
            }
            count++;
        }
    }
    
    private void processNullUmp(char pos, EntityUmpireGamelog entityUmpireGamelog) {
        Person person = new Person("","");
        
        switch (pos) {
            case 'H':
                entityUmpireGamelog.setHomePlateUmpire(getEntityPerson(person));
                break;
            case 'F':
                entityUmpireGamelog.setFirstBaseUmpire(getEntityPerson(person));
                break;
            case 'S':
                entityUmpireGamelog.setSecondBaseUmpire(getEntityPerson(person));
                break;
            case 'T':
                entityUmpireGamelog.setThirdBaseUmpire(getEntityPerson(person));
                break;
            case 'L':
                entityUmpireGamelog.setLeftFieldUmpire(getEntityPerson(person));
                break;
            case 'R': 
                entityUmpireGamelog.setRightFieldUmpire(getEntityPerson(person));
                break;
            default:  
                break;
        }
        
    }
    
    private EntityPerson getEntityPerson(Person person) {
        EntityPerson entityPerson = new EntityPerson();
        entityPerson.setCode(person.getId());  // check, not sure if this is the correct value.
        return entityPerson;
    }

}
