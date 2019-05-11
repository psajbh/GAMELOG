package com.jhart.gamelog.model.gameline;

import com.jhart.gamelog.types.GameLineConstants;
import com.jhart.gamelog.types.GameLineType;
import com.jhart.gamelog.types.LeagueType;
import com.jhart.gamelog.types.LocationType;
import com.jhart.gamelog.utils.Utils;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jhart.gamelog.api.GameLine;
import com.jhart.gamelog.model.parks.Park;
import com.jhart.gamelog.parser.ImportDataManager;

@Data
@EqualsAndHashCode
@ToString 
public class BattingGameLine implements GameLine {
	private static final Logger LOG = LoggerFactory.getLogger(BattingGameLine.class);

	@Getter
	private long gameLogId;
	private LocationType location;
	private GameLineType gameLineType;

	private int ab;
	private int h;
	private int d;
	private int t;
	private int hr;
	private int rbi;
	private int sh;
	private int sf;
	private int hbp;
	private int bb;
	private int ibb;
	private int so;
	private int sb;
	private int cs;
	private int gidp;
	private int awci;  //awarded first on catcher's interference
	private int lob;
	
	public BattingGameLine(long gameLogId) {
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
			case GameLineConstants.AB: 
				setAb(value);
				break;
			case GameLineConstants.H:
				setH(value);
				break;
			case GameLineConstants.D:
				setD(value);
				break;
			case GameLineConstants.T:
				setT(value);
				break;
			case GameLineConstants.HR:
				setHr(value);
				break;
			case GameLineConstants.RBI:
				setRbi(value);
				break;
			case GameLineConstants.SH:
				setSh(value);
				break;
			case GameLineConstants.SF:;
				setSf(value);
				break;
			case GameLineConstants.HBP:
				setHbp(value);
				break;
			case GameLineConstants.BB:
				setBb(value);
				break;
			case GameLineConstants.IBB:
				setIbb(value);
				break;
			case GameLineConstants.SO:
				setSo(value);
				break;
			case GameLineConstants.SB:
				setSb(value);
				break;
			case GameLineConstants.CS:
				setCs(value);
				break;
			case GameLineConstants.GIDP:
				setGidp(value);
				break;
			case GameLineConstants.AWCI:
				setAwci(value);
				break;
			case GameLineConstants.LOB:
				setLob(value);
				break;
			default:
				LOG.debug("No match for batting batting game line entry: " + i);
				break;
		}
	}
}
