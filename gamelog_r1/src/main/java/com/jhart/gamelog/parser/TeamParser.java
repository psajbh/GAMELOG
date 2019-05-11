package com.jhart.gamelog.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
//import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jhart.gamelog.api.Parser;
//import com.jhart.gamelog.model.Gamelog;
//import com.jhart.gamelog.model.Person;
//import com.jhart.gamelog.model.team.Franchise;
import com.jhart.gamelog.model.team.TeamSeason;
import com.jhart.gamelog.types.DivisionType;
import com.jhart.gamelog.types.LeagueType;
//import com.jhart.gamelog.utils.Utils;
import com.jhart.gamelog.utils.Utils;

public class TeamParser implements Parser<TeamSeason>{
	private static final Logger LOG = LoggerFactory.getLogger(TeamParser.class);
	
    private static String START = "/TEAM_";
    private static String END = ".txt";
    private static int MAX = 11;

	public TeamParser(){}
	
	@Override	
	public Map<String, List<TeamSeason>> process(String source){
        String seasonSetup = source.substring(source.indexOf(TeamParser.START) + TeamParser.START.length());
        String season = seasonSetup.substring(0, TeamParser.END.length());
        Map<String, List<TeamSeason>> teamSeasonMap = new HashMap<>();
        
        List<TeamSeason> teamSeasons = new ArrayList<>();
        
        Path p1 = Paths.get(source);
        try (BufferedReader reader = Files.newBufferedReader(p1, Charset.forName(Parser.UTF_8))){
            String currentLine = null;
            int recordNumber = 0;
            while((currentLine=reader.readLine()) != null) {
                if (null != currentLine) {
                	recordNumber++;
                	currentLine = Utils.cleanLine(currentLine, Parser.CLEAN_REPEAT);
                	if(StringUtils.isBlank(currentLine)) {
                		LOG.debug("breaking out at recordNumber: " + recordNumber); //make log statement
                		break;
                	}
                	else {
                        String[] elements = currentLine.split(",");
                        if (elements.length != TeamParser.MAX) {
                            //Log this issue.
                            continue;
                        }
                        teamSeasons.addAll(processElements(elements, season));
                	}
                }
            }
            teamSeasonMap.put(season, teamSeasons);    
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }

        return teamSeasonMap;
	}
	
	private List<TeamSeason> processElements(String[] elements, String currentSeason) {
	    List<TeamSeason> teamSeasons = new ArrayList<>();
	    
		String franchiseId = elements[0];
		String teamId = elements[1];
		LeagueType leagueId = LeagueType.valueOf(elements[2]);
		
		DivisionType divisionId = null;
		if (!elements[3].equals(Parser.EMPTY)){
			divisionId = DivisionType.valueOf(elements[3]);
		}

		String teamName = elements[4];
		String teamNickName = elements[5];
		String teamAltNickName = elements[6];
		String teamStart = elements[7];
		String teamEnd = elements[8];
		String locationCity = elements[9];
		String locationState = elements[10];
		List<String> seasons = getSeasonsByMDY(teamStart, teamEnd, currentSeason);
		
		for (String season : seasons) {
			TeamSeason ts = new TeamSeason();
			ts.setSeason(season);
			ts.setFranchiseId(franchiseId);
			ts.setTeamId(teamId);
			ts.setLeagueId(leagueId);
			ts.setDivisionId(divisionId);
			ts.setTeamName(teamName);
			ts.setTeamNickName(teamNickName);
			ts.setTeamAltNickName(teamAltNickName);
			ts.setTeamStart(teamStart);
			ts.setTeamEnd(teamEnd);
			ts.setCity(locationCity);
			ts.setState(locationState);
			teamSeasons.add(ts);
			LOG.trace(ts.getTag());
		}
		return teamSeasons;
	}
	
	private List<String> getSeasonsByMDY(String start, String end, String currentSeason){
		String[] dateArray = start.split("/");
		String firstSeason = dateArray[2];

		String lastSeason = null;
		if (end.equals(Parser.EMPTY)) {
			lastSeason = currentSeason;
		}
		else {
			dateArray = end.split("/");
			lastSeason = dateArray[2];
		}
		
		List<String> seasons = getSeasons(firstSeason, lastSeason);
		return seasons;
		
	}
	
	private List<String> getSeasons(String season1, String season2){
		List<String> seasons = new ArrayList<>();
		int s1 = Integer.valueOf(season1);
		int s2 = Integer.valueOf(season2);
		
		
		for(int i = s1; i <= s2; i++) {
			String season = String.valueOf(i);
			seasons.add(season);
		}
		
		return seasons;
	}
	
//    private String cleanLine(String line) {
//        for (int i = 0; i < 4; i++) {
//            line = line.replaceAll(",,", ",-1,");
//        }
//        return line;
//    }
	
	
}

