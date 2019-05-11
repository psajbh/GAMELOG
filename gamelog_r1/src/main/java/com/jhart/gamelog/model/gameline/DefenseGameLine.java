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
public class DefenseGameLine implements GameLine {
	private static final Logger LOG = LoggerFactory.getLogger(DefenseGameLine.class);
	
	private int putouts;
	private int assists;
	private int errors;
	private int pb;
	private int dp;
	private int tp;

	@Getter
	private long gameLogId;
	private LocationType location;
	private GameLineType gameLineType;


	public DefenseGameLine(long gameLogId) {
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
			case GameLineConstants.PUTOUTS:
				setPutouts(value);
				break;
			case GameLineConstants.ASSISTS:
				setAssists(value);
				break;
			case GameLineConstants.ERRORS:
				setErrors(value);
				break;
			case GameLineConstants.PASSED_BALLS:
				setPb(value);
				break;
			case GameLineConstants.DOUBLE_PLAYS:
				setDp(value);
				break;
			case GameLineConstants.TRIPLE_PLAYS:
				setTp(value);
				break;
			default:
				LOG.debug("No match for defense game line entry: " + i);
				break;
		}
	}
}
