package com.jhart.gamelog.types;

public enum StartingBattingOrder {
	FIRST(1),
	SECOND(2),
	THIRD(3),
	FOURTH(4),
	FIFTH(5),
	SIXTH(6),
	SEVENTH(7),
	EIGHTH(8),
	NINTH(9);
	
	private int code;
	
	StartingBattingOrder(int code){ 
		this.code = code;
	}
	
	public static StartingBattingOrder getByCode(int order) {
		for(StartingBattingOrder sbo : values()) {
	        if(sbo.getCode() == order) {
	        	return sbo;
	        }
	    }
		return null;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
