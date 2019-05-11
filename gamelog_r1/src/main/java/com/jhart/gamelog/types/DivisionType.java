package com.jhart.gamelog.types;

@Deprecated
public enum DivisionType {
	E("E", "East"),
	C("C", "Central"),
	W("W", "West");
	
	private String code;
	private String name;
	
	DivisionType (String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}
	
}
