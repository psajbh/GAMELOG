package com.jhart.gamelog.parser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.jhart.gamelog.model.Gamelog;
import com.jhart.gamelog.model.Lineup;
import com.jhart.gamelog.model.Player;
import com.jhart.gamelog.model.TeamGameLog;
import com.jhart.gamelog.types.LocationType;
import com.jhart.gamelog.types.StartingPosition;
import com.jhart.gamelog.types.utils.HomeBatOrderUtility;
import com.jhart.gamelog.types.utils.VisitorBatOrderUtility;
import com.jhart.gamelog.types.StartingBattingOrder;
import com.jhart.gamelog.utils.Utils;

public class TeamGameLogBuilder {
    
    Gamelog gamelog;
    String[] elements;
    
    public TeamGameLogBuilder(Gamelog gamelog, String[] elements) {
        this.gamelog = gamelog;
        this.elements = elements;
    }
    
    public Map<LocationType, TeamGameLog> build(){
        Map<LocationType, TeamGameLog> teamLogMap = new HashMap<>();
        teamLogMap.put(LocationType.V, buildVisitingTeamLog());
        teamLogMap.put(LocationType.H, buildHomeTeamLog());
        return teamLogMap;
    }
    
    private TeamGameLog buildVisitingTeamLog() {
        
        TeamGameLog visitingTeamGameLog = new TeamGameLog(gamelog, gamelog.getVisitingTeam(), 
                gamelog.getHomeTeam(), gamelog.getWinStatus(LocationType.V), LocationType.V);
        
        Lineup visitingTeamLineup = new Lineup(gamelog);
        
        String[]  mgrData = Arrays.copyOfRange(elements, 89, 91);
        Player visitingTeamManager = new Player(Utils.stripQuotes(mgrData[1]), Utils.stripQuotes(mgrData[0]));
        visitingTeamLineup.getStartingPositions().put(StartingPosition.M, visitingTeamManager);
        
        String[] startPitcherData = Arrays.copyOfRange(elements, 101, 103);
        Player visitingStarterPitcher = new Player(Utils.stripQuotes(startPitcherData[1]), Utils.stripQuotes(startPitcherData[0]));
        visitingTeamLineup.getStartingPositions().put(StartingPosition.P, visitingStarterPitcher);
        
        Player visitingTeamBatter;
        
        for (int i = 1; i < 10; i++) {
        	StartingBattingOrder sbo = StartingBattingOrder.getByCode(i);
            //String[] visitingTeamBatterData = Arrays.copyOfRange(elements, 105, 108);
            VisitorBatOrderUtility vbo = VisitorBatOrderUtility.getByCode(i);
            String[] visitingTeamBatterData = Arrays.copyOfRange(elements, vbo.getStart(), vbo.getEnd());
            visitingTeamBatter = new Player(Utils.stripQuotes(visitingTeamBatterData[1]),Utils.stripQuotes(visitingTeamBatterData[0]));
            String pos = visitingTeamBatterData[2];
            StartingPosition startingFieldPosition = StartingPosition.getByCode(pos);
            visitingTeamLineup.getStartingPositions().put(startingFieldPosition, visitingTeamBatter);
            visitingTeamLineup.getStartingOrder().put(sbo, visitingTeamBatter);
        }
        visitingTeamGameLog.setLineup(visitingTeamLineup);
        return visitingTeamGameLog;
    }
    
    private TeamGameLog buildHomeTeamLog() {
        TeamGameLog homeTeamGameLog = new TeamGameLog(gamelog, gamelog.getHomeTeam(), 
                gamelog.getVisitingTeam(), gamelog.getWinStatus(LocationType.H), LocationType.H);
        
        Lineup homeTeamLineup = new Lineup(gamelog);
        
        String[] mgrData = Arrays.copyOfRange(elements, 91, 93);
        Player homeTeamManager = new Player(Utils.stripQuotes(mgrData[1]), Utils.stripQuotes(mgrData[0]));
        homeTeamLineup.getStartingPositions().put(StartingPosition.M, homeTeamManager);
        
        String[] startPitcherData = Arrays.copyOfRange(elements, 103, 105);
        Player homeStarterPitcher = new Player(Utils.stripQuotes(startPitcherData[1]), Utils.stripQuotes(startPitcherData[0]));
        homeTeamLineup.getStartingPositions().put(StartingPosition.P, homeStarterPitcher);

        Player homeTeamBatter;
        
        for (int i = 1; i < 10; i++) {
            StartingBattingOrder sbo = StartingBattingOrder.getByCode(i);
            HomeBatOrderUtility hbo = HomeBatOrderUtility.getByCode(i);
            String[] homeTeamBatterData = Arrays.copyOfRange(elements, hbo.getStart(), hbo.getEnd());
            homeTeamBatter = new Player(Utils.stripQuotes(homeTeamBatterData[1]),Utils.stripQuotes(homeTeamBatterData[0]));
            String pos = homeTeamBatterData[2];
            StartingPosition startingFieldPosition = StartingPosition.getByCode(pos);
            homeTeamLineup.getStartingPositions().put(startingFieldPosition, homeTeamBatter);
            homeTeamLineup.getStartingOrder().put(sbo, homeTeamBatter);
        }
        homeTeamGameLog.setLineup(homeTeamLineup);
        return homeTeamGameLog;
    }

}
