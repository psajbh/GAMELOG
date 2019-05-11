package com.jhart.gamelog.api;

import com.jhart.gamelog.model.Gamelog;
import com.jhart.gamelog.model.TeamGameLog;
import com.jhart.gamelog.types.LeagueType;

public interface TeamIntf {
	
	String getName();
	void setName(String name);
	LeagueType getLeague();
	void setLeague(LeagueType league);
	
	TeamGameLog getTeamGameLog(Gamelog gamelog);
	boolean setTeamGameLog(Gamelog gamelog, TeamGameLog teamGameLog);
	

}
