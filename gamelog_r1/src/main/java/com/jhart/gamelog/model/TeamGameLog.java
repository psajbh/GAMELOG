package com.jhart.gamelog.model;

import com.jhart.gamelog.model.team.Team;
import com.jhart.gamelog.types.LocationType;
import com.jhart.gamelog.types.WinStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString 
public class TeamGameLog {
	private Gamelog gamelog;
	private Team team;
	private Team opponent;
	private Lineup lineup;
	private WinStatus winStatus;
	private LocationType location;
	
	public TeamGameLog(Gamelog gamelog, Team team, Team opponent, 
			WinStatus winStatus, LocationType location ) {
		this.gamelog = gamelog;
		this.team = team;
		this.opponent = opponent;
		this.winStatus = winStatus;
		this.location = location;
	}
}
