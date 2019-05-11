package com.jhart.gamelog.utils.appconfig;

public enum AppPropType {
	
	DATAFILE_LOCATION("DATAFILE_LOCATION","_DATAFILES");
			
	private String code;
	private String value;
	
	AppPropType(String code, String value){
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
