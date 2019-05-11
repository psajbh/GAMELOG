package com.jhart.gamelog.model.team;

import com.jhart.gamelog.api.model.IModel;
//import com.jhart.gamelog.model.Gamelog;
import com.jhart.gamelog.types.DivisionType;
import com.jhart.gamelog.types.LeagueType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString 
@Data
public class TeamSeason implements IModel{
    long id;
	String franchiseId;
	String teamId;
	LeagueType leagueId;
	DivisionType divisionId;
	String teamName;
	String teamNickName;
	String teamAltNickName;
	String teamStart;
	String teamEnd;
	String city;
	String state;
	String season;
	
	public TeamSeason(){}
	
	public String getTag() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.season).append("|");
		builder.append(this.teamId).append("|");
		builder.append(this.leagueId.name());
		builder.append("|");
		builder.append(this.franchiseId);
		return builder.toString();
	}
}	
	
