package com.jhart.gamelog.model;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jhart.gamelog.exception.GamelogException;
import com.jhart.gamelog.model.gameline.PitchingGameLine;
import com.jhart.gamelog.model.team.HomeTeam;
import com.jhart.gamelog.model.team.VisitingTeam;
import com.jhart.gamelog.types.DayOrNight;
import com.jhart.gamelog.types.GameLineType;
import com.jhart.gamelog.types.GameType;
import com.jhart.gamelog.types.LeagueType;
import com.jhart.gamelog.types.LocationType;
import com.jhart.gamelog.types.WeekDay;
import com.jhart.gamelog.types.WinStatus;
import com.jhart.gamelog.utils.Utils;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import com.jhart.gamelog.api.GameLine;
import com.jhart.gamelog.api.model.IModel;


@Slf4j
@Data
@EqualsAndHashCode
@ToString 
public class Gamelog implements IModel{
    private long id;               // id will represent a database primary key, not processed as part of parse.
	private int gameLogId;        // every gameLog will have a unique ID from parser
	private String season;         // captured from parser source.
	private String strDate;        //ref: 1. Date in the form "yyyymmdd", ex: strDate=20170402
	
	@Getter
	private GameType gameType;     //ref: 2. Number of game, ex: gameType=ZERO 
	
	@Getter
	private WeekDay weekDay;       //ref: 3. Day of week  ("Sun","Mon","Tue","Wed","Thu","Fri","Sat") ex: weekDay=SUN
	
	private int gameTotalOuts;     // ex: gameTotalOuts=53
	
	@Getter
	private DayOrNight dayOrNight; // ex: dayOrNight=D
	private String parkId;         // ex: parkId=PHO01
	private int attendance;        // ex: attendance=49016
	@Getter
	private int timeOfGame;        // ex: timeOfGame=203  (minute)

	@Getter
	private String visitingTeamGameScore; //  ex: 010011101
	
	@Getter
	private String homeTeamGameScore;     //  ex: 000003012
	
	@Getter
	private int visitingTeamScore;         //  ex: 5
	@Getter
	private int homeTeamScore;             //  ex: 6

	@Getter
	private VisitingTeam visitingTeam;    // ex:Team [name=SFN, league=NL]
	@Getter
	private HomeTeam homeTeam;            // ex:Team [name=ARI, league=NL]
	
	@Getter
	private LeagueType visitingTeamleague;    // remove hold data in Team object
	@Getter
	private LeagueType homeTeamleague;        // remove hold data in Team object
	
	@Getter
	private int visitingTeamGameNumber;   // remove hold data in Team object
	@Getter
	private int homeTeamGameNumber;       // remove hold data in Team object
	
	private Map<GameLineType, GameLine> gameLines = new HashMap<>();
		
	private UmpireGameLog umpireGameLog;  //holds map of umpires by position.
	private Map<LocationType, TeamGameLog> teamLogMap;
	private Player winningPitcher;
	private Player losingPitcher;
	private Player savingPitcher;
	private Player gwRbiPlayer;
	

    public Gamelog() {}
    
    //called by GameLineBuilder
    public void addGameLine(final GameLine gameLine) {
        GameLineType gameLineType = gameLine.getGameLineType();
        if (null == gameLines.get(gameLineType)) {
            gameLines.put(gameLineType, gameLine);
        }
    }
    
    // called by TeamGameLog
    public WinStatus getWinStatus(LocationType location) {
        
        if (visitingTeamScore == homeTeamScore) {
            return WinStatus.T;
        }
        
        if (location == LocationType.V) {
            if (visitingTeamScore > homeTeamScore) {
                return WinStatus.W;
            }
        }
            
        if (location == LocationType.H) {
            if (homeTeamScore  > visitingTeamScore) {
                return WinStatus.W;
            }
        }
        
        return WinStatus.L;
    }


	public void setTimeOfGame(String strtimeOfGame) {
		setTimeOfGame(Utils.convertStringToInt(strtimeOfGame));
	}
	
	public void setTimeOfGame(final int timeOfGame) {
		this.timeOfGame = timeOfGame;
	}

	public void setAttendance(final String attendance) {
		log.trace("setAttendance: " + attendance);
	    if (!StringUtils.isBlank(attendance)){
	        setAttendance(Utils.convertStringToInt(attendance));
	    }
	}
	
	public void setAttendance(int attendance) {
	    if (attendance < 0) {
	        log.debug("attendance value not reported for gamelog: "+ this.getGameLogId());
	        
	    }
	    else {
	        this.attendance = attendance;
	    }
	}
	
	public void setDayOrNight(final String dayornight) {
		this.dayOrNight = DayOrNight.valueOf(dayornight);
	}

	public int getGameTotalOuts() {
		return gameTotalOuts;
	}

	public void setGameTotalOuts(String gameTotalOuts) {
		setGameTotalOuts(Utils.convertStringToInt(gameTotalOuts));
	}
	
	public void setGameTotalOuts(final int gameTotalOuts) {
		this.gameTotalOuts = gameTotalOuts;
	}

	public void setHomeTeamGameNumber(String homeTeamGameNumber) throws GamelogException{
		try {
			setHomeTeamGameNumber(Utils.convertStringToInt(homeTeamGameNumber));
		}
		catch(NumberFormatException nfe) {
			throw new GamelogException(nfe);
		}
	}

	public void setHomeTeamGameNumber(final int homeTeamGameNumber) {
		this.homeTeamGameNumber = homeTeamGameNumber;
	}
	
	public void setVisitingTeamGameNumber(String visitingTeamGameNumber) throws GamelogException{
		try {
			setVisitingTeamGameNumber(Utils.convertStringToInt(visitingTeamGameNumber));
		}
		catch(NumberFormatException nfe) {
			throw new GamelogException(nfe);
		}
	}

	public void setVisitingTeamGameNumber(final int visitingTeamGameNumber) {
		this.visitingTeamGameNumber = visitingTeamGameNumber;
	}

	public void setWeekDay(String strWeekDay) {
		setWeekDay(WeekDay.valueOf(strWeekDay));
	}

	public void setWeekDay(final WeekDay weekDay) {
		this.weekDay = weekDay;
	}
	
	
	public void setGameType(String strGameType) {
		GameType a = GameType.getValueFromCode(strGameType);
		setGameType(a);
	}

	public void setGameType(final GameType gameType) {
		this.gameType = gameType;
	}

	public void setVisitingTeamLeague(String visitingTeamLeague) {
		setVisitingTeamleague(LeagueType.valueOf(visitingTeamLeague));
		VisitingTeam visitingTeam = getVisitingTeam();
		visitingTeam.setLeague(LeagueType.valueOf(visitingTeamLeague));
	}
	
	public void setVisitingTeamleague(final LeagueType visitingTeamleague) {
		this.visitingTeamleague = visitingTeamleague;
	}
	
	public void setHomeTeamLeague(String homeTeamLeague) {
		setHomeTeamleague(LeagueType.valueOf(homeTeamLeague));
		HomeTeam homeTeam = getHomeTeam();
		homeTeam.setLeague(LeagueType.valueOf(homeTeamLeague));
	}

	public void setHomeTeamleague(final LeagueType homeTeamleague) {
		this.homeTeamleague = homeTeamleague;
	}
	
	public void setVisitingTeam(final String visitingTeamName) {
		VisitingTeam visitingTeam = new VisitingTeam();
		visitingTeam.setName(visitingTeamName);
		setVisitingTeam(visitingTeam);
	}

	public void setVisitingTeam(final VisitingTeam visitingTeam) {
		this.visitingTeam = visitingTeam;
	}

	public void setHomeTeam(String homeTeamName) {
		HomeTeam homeTeam = new HomeTeam();
		homeTeam.setName(homeTeamName);
		setHomeTeam(homeTeam);
	}
	
	public void setHomeTeam(final HomeTeam homeTeam) {
		this.homeTeam = homeTeam;
	}
	
	public void setVisitingTeamScore(String visitingTeamScore) {
		setVisitingTeamScore(Utils.convertStringToInt(visitingTeamScore));
	}
	
	public void setVisitingTeamScore(final int visitingTeamScore) {
		this.visitingTeamScore = visitingTeamScore;
	}

	public void setHomeTeamScore(String homeTeamScore) {
		setHomeTeamScore(Utils.convertStringToInt(homeTeamScore));
	}

	public void setHomeTeamScore(final int homeTeamScore) {
		this.homeTeamScore = homeTeamScore;
	}

	
}
