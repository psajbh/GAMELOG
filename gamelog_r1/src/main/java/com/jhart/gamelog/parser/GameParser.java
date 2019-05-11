package com.jhart.gamelog.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jhart.gamelog.api.Parser;
import com.jhart.gamelog.model.Gamelog;
import com.jhart.gamelog.model.Player;
import com.jhart.gamelog.model.TeamGameLog;
import com.jhart.gamelog.types.LocationType;
import com.jhart.gamelog.utils.Utils;


public class GameParser implements Parser<Gamelog>{
	private static final Logger LOG = LoggerFactory.getLogger(GameParser.class);
    private static String START = "/GL_";
    private static String END = ".txt";
	
	public Map<String, List<Gamelog>> process(String source) {
	    Map<String, List<Gamelog>> gameLogMap = new HashMap<>();
		List<Gamelog> gameLogs = new ArrayList<>();
	    String seasonSetup = source.substring(source.indexOf(GameParser.START) + GameParser.START.length());
	    String season = seasonSetup.substring(0, GameParser.END.length());
		
		Path p1 = Paths.get(source);
		try (BufferedReader reader = Files.newBufferedReader(p1, Charset.forName(Parser.UTF_8))){
			String currentLine = null;
			int l = 0;
			while((currentLine=reader.readLine()) != null) {
				if (null != currentLine) {
					Gamelog gamelog = new Gamelog();
					gamelog.setSeason(season);
					gamelog.setGameLogId(l++);
					
					String[] elements = currentLine.split(",");
					
					//gamelog.setSeason();
					gamelog.setStrDate(Utils.stripQuotes(elements[0]));
					gamelog.setGameType(Utils.stripQuotes(elements[1]));
					gamelog.setWeekDay(Utils.stripQuotes(elements[2]).toUpperCase());
					
					gamelog.setVisitingTeam(Utils.stripQuotes(elements[3]).toUpperCase());
					gamelog.setVisitingTeamLeague(Utils.stripQuotes(elements[4]).toUpperCase());
					gamelog.setVisitingTeamGameNumber(Utils.stripQuotes(elements[5]).toUpperCase());
					gamelog.setHomeTeam(Utils.stripQuotes(elements[6]).toUpperCase());
					gamelog.setHomeTeamLeague(Utils.stripQuotes(elements[7]).toUpperCase());
					gamelog.setHomeTeamGameNumber(Utils.stripQuotes(elements[8]).toUpperCase());
					
					gamelog.setVisitingTeamScore(elements[9]);
					gamelog.setHomeTeamScore(elements[10]);
					gamelog.setGameTotalOuts(elements[11]);
					gamelog.setDayOrNight(Utils.stripQuotes(elements[12]));
					
					gamelog.setParkId(Utils.stripQuotes(elements[16]));
					gamelog.setAttendance(elements[17]);
					gamelog.setTimeOfGame(elements[18]);
					gamelog.setVisitingTeamGameScore(Utils.stripQuotes(elements[19]));
					gamelog.setHomeTeamGameScore(Utils.stripQuotes(elements[20]));
					
					//get only part of an Array in Java?:https://stackoverflow.com/questions/11001720/get-only-part-of-an-array-in-java 
					String[] visitingTeamlineScore= Arrays.copyOfRange(elements, 21, 38);
					String[] visitingTeamPitchingData = Arrays.copyOfRange(elements, 38, 43);
					String[] visitingTeamDefenseData = Arrays.copyOfRange(elements, 43, 49);
					String[] homeTeamlineScore= Arrays.copyOfRange(elements, 49, 66);
					String[] homeTeamPitchingData = Arrays.copyOfRange(elements, 66, 71);
					String[] homeTeamDefenseData = Arrays.copyOfRange(elements, 71, 77);
					
					//process gameLines
					GameLineBuilder builder = new GameLineBuilder(gamelog.getId());
					if(builder.setup(visitingTeamlineScore, visitingTeamPitchingData, 
							visitingTeamDefenseData, homeTeamlineScore, 
							homeTeamPitchingData, homeTeamDefenseData)) {
						builder.build(gamelog);	
					}
					//process umpires
					String[] umpireData = Arrays.copyOfRange(elements, 77, 89);
					UmpireBuilder umpireBuilder = new UmpireBuilder(gamelog.getGameLogId());
					gamelog.setUmpireGameLog(umpireBuilder.build(umpireData));

					//process key players of game
					String[]  playerOfRecord = Arrays.copyOfRange(elements, 93, 95);
					Player winningPitcher = new Player(Utils.stripQuotes(playerOfRecord[1]), Utils.stripQuotes(playerOfRecord[0]));
					gamelog.setWinningPitcher(winningPitcher);
					
					playerOfRecord = Arrays.copyOfRange(elements, 95, 97);
					Player losingPitcher = new Player(Utils.stripQuotes(playerOfRecord[1]), Utils.stripQuotes(playerOfRecord[0]));
					gamelog.setLosingPitcher(losingPitcher);
					
					playerOfRecord = Arrays.copyOfRange(elements, 97, 99);
					Player savingPitcher = new Player(Utils.stripQuotes(playerOfRecord[1]), Utils.stripQuotes(playerOfRecord[0]));
					gamelog.setSavingPitcher(savingPitcher);
					
                    playerOfRecord = Arrays.copyOfRange(elements, 99, 101);
                    Player gwRbiPlayer = new Player(Utils.stripQuotes(playerOfRecord[1]), Utils.stripQuotes(playerOfRecord[0]));
                    gamelog.setGwRbiPlayer(gwRbiPlayer);
					
                    //process teamGameLogs
                    TeamGameLogBuilder teamGameLogBuilder = new TeamGameLogBuilder(gamelog, elements);
                    Map<LocationType, TeamGameLog> teamLogMap = teamGameLogBuilder.build();
                    gamelog.setTeamLogMap(teamLogMap);
                    
					gameLogs.add(gamelog);
				}
			}
			gameLogMap.put(season, gameLogs);
			// if debug logging call this.
			//viewGameLogs(gameLogs);
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
		
		return gameLogMap;
	}
	
	@SuppressWarnings("unused")
    private void viewGameLogs(List<Gamelog> gameLogs) {
		if (null != gameLogs) {
			for (Gamelog gLog : gameLogs) {
				LOG.debug(gLog.toString());
			}
		}
	}

}

