package com.jhart.gamelog.types;

public enum UmpirePosition {
	
	HP ("HP", "Home Plate"),
	FB ("1B", "First Base"),
	SB ("2B", "Second Base"),
	TB ("3B", "Third Base"),
	LF ("LF", "Left Field"),
	RF ("RF", "Right Field");

	private String code;
	private String description;
	
	UmpirePosition(String code, String description){
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	
	
}
