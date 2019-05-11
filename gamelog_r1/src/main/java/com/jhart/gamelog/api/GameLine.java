package com.jhart.gamelog.api;

import com.jhart.gamelog.types.GameLineType;

public interface GameLine {
	
	GameLineType getGameLineType();
	void setGameLineType(GameLineType gameLineType);
	long getGameLogId();
	

}
