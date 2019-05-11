package com.jhart.gamelog.model.gameline;

import com.jhart.gamelog.types.GameLineConstants;
import com.jhart.gamelog.types.GameLineType;
import com.jhart.gamelog.types.LocationType;
import com.jhart.gamelog.utils.Utils;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jhart.gamelog.api.GameLine;

@Data
@EqualsAndHashCode
@ToString 
public class PitchingGameLine implements GameLine {
	private static final Logger LOG = LoggerFactory.getLogger(PitchingGameLine.class);
	
	@Getter
	private long gameLogId;
	private LocationType location;
	private GameLineType gameLineType;

    private int pitchersUsed;   //1 is a complete game
	private int individualEr;
	private int teamEr;
	private int wp;
	private int balks;

	public PitchingGameLine(long gameLogId) {
		this.gameLogId = gameLogId;
	}
	
	public void process(String[] teamLineScore) {
		int i = 0;
		for (String value : teamLineScore) {
			set(i++, Utils.convertStringToInt(value));
		}
	}

	private void set(int i, int value) {
		switch (i) {
			case GameLineConstants.PITCHERS_USED: 
				setPitchersUsed(value);
				break;
			case GameLineConstants.INDIVIDUAL_ER:
				setIndividualEr(value);
				break;
			case GameLineConstants.TEAM_ER:
				setTeamEr(value);
				break;
			case GameLineConstants.WP:
				setWp(value);
				break;
			case GameLineConstants.BALKS:
				setBalks(value);
				break;
			default:
				LOG.debug("No match for pitching game line entry: " + i);
				break;
		}
	}
}
