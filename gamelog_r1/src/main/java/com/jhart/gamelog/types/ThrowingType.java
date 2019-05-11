package com.jhart.gamelog.types; 

public enum ThrowingType {
	R("R", "Righthanded"),
	L("L", "Lefthanded"),
	U("U", "Unk");
	
	String code;
	String description;
	
	ThrowingType(String code, String desc){
		this.code = code;
		this.description = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	
	

}
