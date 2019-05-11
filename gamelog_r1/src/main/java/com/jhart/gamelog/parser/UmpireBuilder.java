package com.jhart.gamelog.parser;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jhart.gamelog.model.Umpire;
import com.jhart.gamelog.model.UmpireGameLog;
import com.jhart.gamelog.transform.GamelogTransformer;
import com.jhart.gamelog.types.UmpirePosition;
import com.jhart.gamelog.utils.Utils;

public class UmpireBuilder {
    private static final Logger LOG = LoggerFactory.getLogger(UmpireBuilder.class);

	private int gameLogId;
	private Map<UmpirePosition, Umpire> umpireRepo = new HashMap<>();

	public UmpireBuilder(int gameLogId) {
		this.gameLogId = gameLogId;
	}
	
	public UmpireGameLog build(String[] umpireData) {
	    //LOG.debug("UmpireBuilder:build - ");
		UmpireGameLog umpireGameLog = new UmpireGameLog(gameLogId);
		
		createUmpireByPosition(umpireData[0], umpireData[1], UmpirePosition.HP);
		createUmpireByPosition(umpireData[2], umpireData[3], UmpirePosition.FB);
		createUmpireByPosition(umpireData[4], umpireData[5], UmpirePosition.SB);
		createUmpireByPosition(umpireData[6], umpireData[7], UmpirePosition.TB);
		createUmpireByPosition(umpireData[8], umpireData[9], UmpirePosition.LF);
		createUmpireByPosition(umpireData[10], umpireData[11], UmpirePosition.RF);
		umpireGameLog.setGameUmps(umpireRepo);
			
	    return umpireGameLog;
	}
	
	private void createUmpireByPosition(String name, String code, UmpirePosition position){
		//Umpire umpire = new Umpire(Utils.stripQuotes(name), Utils.stripQuotes(code));
	    Umpire umpire = new Umpire(Utils.stripQuotes(code), Utils.stripQuotes(name));
		if (umpire.getName().equals("(none)")){
			umpireRepo.put(position, new Umpire("",""));
		}
		else {
			umpireRepo.put(position, umpire);
		}
	}
	
	

}
