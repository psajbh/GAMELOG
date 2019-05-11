package com.jhart.gamelog.model;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.jhart.gamelog.model.team.TeamSeason;
import com.jhart.gamelog.types.DivisionType;
import com.jhart.gamelog.types.LeagueType;
import com.jhart.gamelog.types.StartingBattingOrder;
import com.jhart.gamelog.types.StartingPosition;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString 
@Data
public class Lineup {
	
	private Gamelog gamelog;
	private Map<StartingPosition, Player> startingPositions = new HashMap<>();
	private  Map<StartingBattingOrder, Player> startingOrder = new HashMap<>();
	
	public Lineup(Gamelog gamelog) {
		this.gamelog = gamelog;
	}

}
