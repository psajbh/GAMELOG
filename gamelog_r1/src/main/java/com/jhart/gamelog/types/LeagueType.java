package com.jhart.gamelog.types;

//@Deprecated
public enum LeagueType {
	
	NL("NL", "National League"), 
	AL("AL", "American League"),
	NA("NA", "National Association"),
	AA("AA", "American Association"),
	FL("FL", "Federal League"),
	PL("PL", "Players League"),
	UD("UD", "Undetermined"),
	UA("UA", "Union Association");

	private final String code;
	private final String description;
	
	LeagueType(String code, String description){
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
