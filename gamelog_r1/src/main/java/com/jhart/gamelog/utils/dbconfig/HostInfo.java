package com.jhart.gamelog.utils.dbconfig;

public enum HostInfo {

	DESKTOP_C44K8NO("DESKTOP_C44K8NO", "DESKTOP-C44K8NO"), 
	JOHN_ASUS1PC("JOHN_ASUS1PC", "JOHN-ASUS1PC"),
	DESKTOP_RSS2Q3C("DESKTOP_RSS2Q3C", "DESKTOP_RSS2Q3C"),
	UNDETERMINED("UNDETERMINED", "Undetermined");

	private String code;
	private String name;

	HostInfo(String code, String name) {
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
