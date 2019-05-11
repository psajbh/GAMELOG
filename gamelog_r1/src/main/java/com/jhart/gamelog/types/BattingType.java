package com.jhart.gamelog.types;

public enum BattingType {
	R("R", "Right", 2),
	L("L", "Left", 1),
	S("B", "Both", 3),
	U("U", "Unk", 4);
	
	String code;
	String description;
	int id;
	
	BattingType(String code, String desc, int id){
		this.code = code;
		this.description = desc;
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	public int getId() {
		return id;
	}
	

}
