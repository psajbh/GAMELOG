package com.jhart.gamelog.transform;

import com.jhart.gamelog.api.entities.IEntity;
import com.jhart.gamelog.api.model.IModel;
import com.jhart.gamelog.api.transform.Transformer;
import com.jhart.gamelog.entities.location.EntityLocation;
import com.jhart.gamelog.entities.team.EntityDivision;
import com.jhart.gamelog.entities.team.EntityFranchise;
import com.jhart.gamelog.entities.team.EntityLeague;
import com.jhart.gamelog.entities.team.EntitySeason;
import com.jhart.gamelog.entities.team.EntityTeam;
import com.jhart.gamelog.model.team.Team;
import com.jhart.gamelog.model.team.TeamSeason;
import com.jhart.gamelog.types.DivisionType;
import com.jhart.gamelog.types.LeagueType;

public class TeamTransformer implements Transformer{
    
    @SuppressWarnings("deprecation")
	@Override
    public IEntity transform (IModel team) {
        TeamSeason modelTeam = (TeamSeason) team;
        EntityTeam entityTeam = new EntityTeam();
        entityTeam.setCode(modelTeam.getTeamId());
        entityTeam.setName(modelTeam.getTeamName());
        entityTeam.setAlias(modelTeam.getTeamNickName());
        
        
        EntitySeason season  = new EntitySeason();
        season.setCode(modelTeam.getSeason());
        season.setValue(Integer.valueOf(season.getValue()));
        entityTeam.setSeason(season);
        
        
        EntityLocation location = new EntityLocation();
        location.setCity(modelTeam.getCity());
        location.setStateCode(modelTeam.getState());
        entityTeam.setLocation(location);
        
        EntityLeague entityLeague = new EntityLeague();
        LeagueType leagueType = modelTeam.getLeagueId();
        entityLeague.setCode(leagueType.getCode());
        entityLeague.setName(leagueType.getDescription());
        
        DivisionType divisionType = modelTeam.getDivisionId();
        if (divisionType != null) {
        	EntityDivision entityDivision = new EntityDivision();
        	entityDivision.setCode(divisionType.getCode());
        	entityDivision.setName(divisionType.getName());
        	entityLeague.setDivision(entityDivision);
        }
        entityTeam.setLeague(entityLeague);
        
        EntityFranchise entityFranchise = new EntityFranchise();
        entityFranchise.setCode(modelTeam.getFranchiseId());
        entityTeam.setFranchise(entityFranchise);

        return entityTeam;
        
    }
    
    @Override
    public IModel transform(IEntity entityTeam) {
        
        return null;
    }
    

}
