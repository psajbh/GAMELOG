package com.jhart.gamelog.types;

public enum LocationType {
	H("H","Home"),
	V("V", "Visitor");
	
	String code;
	String value;
	
	LocationType(String code, String value){
		this.code = code;
		this.value = value;
	}

}
