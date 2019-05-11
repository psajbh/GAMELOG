package com.jhart.gamelog.parser;

import com.jhart.gamelog.model.Gamelog;
import com.jhart.gamelog.model.gameline.BattingGameLine;
import com.jhart.gamelog.model.gameline.DefenseGameLine;
import com.jhart.gamelog.model.gameline.PitchingGameLine;
import com.jhart.gamelog.types.GameLineType;
import com.jhart.gamelog.types.LocationType;

public class GameLineBuilder {

	private static int LINE_SCORE_SIZE = 17;
	private static int PITCHING_DATA_SIZE = 5;
	private static int DEFENSIVE_DATA_SIZE = 6;
	
	private String[] visitingTeamlineScore;
	private String[] visitingTeamPitchingData; 
	private String[] visitingTeamDefenseData;
	private String[] homeTeamlineScore;
	private String[] homeTeamPitchingData;
	private String[] homeTeamDefenseData;
	
	private long gameLogId;
	
	public GameLineBuilder(long gameLogId) {
		this.gameLogId = gameLogId; 
	}
	
	//validates the data passed in from GameParser.
	public boolean setup(String[] visitingTeamlineScore, 
						String[] visitingTeamPitchingData, 
						String[] visitingTeamDefenseData,
						String[] homeTeamlineScore,
						String[] homeTeamPitchingData,
						String[] homeTeamDefenseData) {
		
		boolean valid = validate(visitingTeamlineScore, homeTeamlineScore, GameLineBuilder.LINE_SCORE_SIZE);
		//34,11,2,1,2,5,0,2,0,5,0, 7,2,1,0,0,9,| 5,6,6,0, 0,  |26, 9, 0, 0, 1,  0,
		//38,13,1,1,1,6,0,0,0,0,0,12,0,0,1,0,6,| 6,5,5,2, 0,  |27, 8, 0, 0 ,0,  0,"gibsg901"
		if (valid) {
			valid = validate(visitingTeamPitchingData, homeTeamPitchingData, GameLineBuilder.PITCHING_DATA_SIZE);
		}
		if (valid) {
			valid = validate(visitingTeamDefenseData, homeTeamDefenseData, GameLineBuilder.DEFENSIVE_DATA_SIZE);
		}
		
		if (valid) {
			this.visitingTeamlineScore = visitingTeamlineScore;
			this.visitingTeamPitchingData = visitingTeamPitchingData;
			this.visitingTeamDefenseData = visitingTeamDefenseData;
			this.homeTeamlineScore = homeTeamlineScore; 
			this.homeTeamPitchingData = homeTeamPitchingData;
			this.homeTeamDefenseData = homeTeamDefenseData; 
		}
		
		return valid;
		
	}
	
	public void build(Gamelog gameLog) {
		BattingGameLine gameLine = new BattingGameLine(gameLogId);
		gameLine.setLocation(LocationType.V);
		gameLine.setGameLineType(GameLineType.VB);
		gameLine.process(visitingTeamlineScore);
		gameLog.addGameLine(gameLine);
		
		gameLine = new BattingGameLine(gameLogId);
		gameLine.setLocation(LocationType.H);
		gameLine.setGameLineType(GameLineType.HB);
		gameLine.process(homeTeamlineScore);
		gameLog.addGameLine(gameLine);
		
		PitchingGameLine pitchingGameLine =  new PitchingGameLine(gameLogId);
		pitchingGameLine.setLocation(LocationType.V);
		pitchingGameLine.setGameLineType(GameLineType.VP);
		pitchingGameLine.process(visitingTeamPitchingData);
		gameLog.addGameLine(pitchingGameLine);
		
		pitchingGameLine =  new PitchingGameLine(gameLogId);
		pitchingGameLine.setLocation(LocationType.H);
		pitchingGameLine.setGameLineType(GameLineType.HP);
		pitchingGameLine.process(visitingTeamPitchingData);
		gameLog.addGameLine(pitchingGameLine);
		
		DefenseGameLine defenseGameLine =  new DefenseGameLine(gameLogId);
		defenseGameLine.setLocation(LocationType.V);
		defenseGameLine.setGameLineType(GameLineType.VD);
		defenseGameLine.process(visitingTeamDefenseData);
		gameLog.addGameLine(defenseGameLine);
		
		defenseGameLine =  new DefenseGameLine(gameLogId);
		defenseGameLine.setLocation(LocationType.H);
		defenseGameLine.setGameLineType(GameLineType.HD);
		defenseGameLine.process(homeTeamDefenseData);
		gameLog.addGameLine(defenseGameLine);
	}
	
	private boolean validate(String[] array1, String[] array2, int size) {
		boolean valid = false;
		if (null != array1 && array1.length == size) {
			valid = true;
		}
		else if (valid){
			if (null != array2 && array2.length == size) {
				valid = true;
			}
		}
		
		return valid;
	}

}
