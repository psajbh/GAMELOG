package com.jhart.gamelog.model.team;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jhart.gamelog.api.TeamIntf;
import com.jhart.gamelog.model.Gamelog;
import com.jhart.gamelog.model.TeamGameLog;
import com.jhart.gamelog.parser.ImportDataManager;
import com.jhart.gamelog.types.LeagueType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString 
public class Team implements TeamIntf{
    private static final Logger LOG = LoggerFactory.getLogger(Team.class);
    
	private String name;
	private LeagueType league;
	private Map<Gamelog,TeamGameLog> gameData = new HashMap<>();
	
	public Team(){}

	@Override
	public boolean setTeamGameLog(Gamelog gameLog, TeamGameLog teamGameLog) {
	    LOG.debug("Team:setTeamGameLog - team: " + getName());
		boolean rValue = false;
		if (null == gameData.get(gameLog)) {
			gameData.put(gameLog, teamGameLog);
			rValue = true;
		}
		return rValue;
	}

	@Override
	public TeamGameLog getTeamGameLog(Gamelog gamelog) {
	    LOG.debug("Team:getTeamGameLog - team: " + getName());
		return gameData.get(gamelog);
	}
	
//	@Override
//	public String getName() {
//		return name;
//	}
	
//	@Override
//	public void setName(String name) {
//		this.name = name;
//	}
	
//	@Override
//	public League getLeague() {
//		return league;
//	}
	
//	@Override
//	public void setLeague(League league) {
//		this.league = league;
//	}

//	@Override
//	public String toString() {
//		return "Team [name=" + name + ", league=" + league + "]";
//	}
}
