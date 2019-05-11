package com.jhart.gamelog.types;

public enum DayOrNight {
	D("D", "Day"),
	N("N", "Night");
	
	String code;
	String description;
	
	DayOrNight(String code, String description) {
		this.code = code;
		this.description = description;
	}

	String getCode() {
		return code;
	}

	String getDescription() {
		return description;
	}
	
	
	
}
