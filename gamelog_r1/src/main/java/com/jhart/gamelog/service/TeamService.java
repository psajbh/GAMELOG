package com.jhart.gamelog.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jhart.gamelog.dao.TeamDao;
import com.jhart.gamelog.entities.team.EntityTeam;
import com.jhart.gamelog.exception.DbExecutionException;
import com.jhart.gamelog.model.team.TeamSeason;
import com.jhart.gamelog.utils.dbconfig.exception.NullSessionException;

public class TeamService {
    private static final Logger LOG = LoggerFactory.getLogger(TeamService.class);
	
	public TeamService() {}
	
    public boolean persist(List<TeamSeason> teamList) {
		LOG.debug("TeamService:persist-1  - potentially " + ((teamList == null) ? 0 : teamList.size()) + " records");
		boolean rValue = false;
		//determine what has already been peristed.
		Map<String, EntityTeam> persistedTeams = new HashMap<>();
		List<TeamSeason> saveList = new ArrayList<>();
		
		TeamDao gle = new TeamDao();
        for(EntityTeam entityTeam : gle.getPersistedTeams()) {
            persistedTeams.put(entityTeam.getCode() + entityTeam.getSeason().getCode(),entityTeam);
        }
		
        LOG.debug("TeamService:persist-2 number of persisted teams: " + persistedTeams.size());
		
        if (persistedTeams.size() > 0) {
        	for (TeamSeason teamSeason : teamList) {
        	    String key = teamSeason.getTeamId() + teamSeason.getSeason();
        		if (persistedTeams.get(key)==null){
        			saveList.add(teamSeason);
        		}
        	}
        }
        else {
        	saveList.addAll(teamList);
        }
        
        LOG.debug("TeamService:persist-3 - saveList size: " + saveList.size());
        if (saveList.size() > 0) {
        	try {
        		gle.saveAll(saveList);
        		rValue = true;
        	}
            catch(NullSessionException nse) {
                LOG.error("TeamService:persist-4 - failed to establish a hibernate session", nse);
            }
            catch(DbExecutionException dbe) {
                LOG.error("TeamService:persist-5 to execute db functionality: " + dbe.getMessage()  );
            }
        }
        LOG.debug("TeamService:persist-6 - finished with rValue: " + rValue);
		return rValue;
	}

}

// id=0, franchiseId=ANA, teamId=LAA, leagueId=AL, divisionId=null, teamName=Los Angeles, teamNickName=Angels, teamAltNickName=-1, teamStart=4/11/1961, teamEnd=9/1/1965, city=Los Angeles, state=CA, season=1961)
// id=0, franchiseId=ANA, teamId=LAA, leagueId=AL, divisionId=null, teamName=Los Angeles, teamNickName=Angels, teamAltNickName=-1, teamStart=4/11/1961, teamEnd=9/1/1965, city=Los Angeles, state=CA, season=1961)

