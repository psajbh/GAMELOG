package com.jhart.gamelog.utils.dbconfig;

public enum SqlPropType {
	
	DRIVER("DRIVER", "_DRIVER"),
	URL("URL", "_URL"),
	USER("USER", "_USER"),
	PASS("PASS", "_PASS"),
	DIALECT("DIALECT", "_DIALECT");
	
	private String code;
	private String value;
	
	SqlPropType(String code, String value){
		this.code = code;
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}


}
