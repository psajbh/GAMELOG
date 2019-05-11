package com.jhart.gamelog.model;

import java.util.HashMap;
import java.util.Map;

import com.jhart.gamelog.api.GameLine;
import com.jhart.gamelog.types.GameLineType;
import com.jhart.gamelog.types.LocationType;
import com.jhart.gamelog.types.UmpirePosition;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString 
public class UmpireGameLog {
	
	private int gameLogId;
	private Map<UmpirePosition, Umpire> gameUmps = new HashMap<>();

	public UmpireGameLog(int gameLogId) {
		this.gameLogId = gameLogId;
	}
}
