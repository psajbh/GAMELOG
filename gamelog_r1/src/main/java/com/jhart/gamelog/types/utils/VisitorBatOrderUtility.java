package com.jhart.gamelog.types.utils;

public enum VisitorBatOrderUtility {
	FIRST(1,105,108),
	SECOND(2,108, 111),
	THIRD(3, 111, 114),
	FOURTH(4, 114, 117),
	FIFTH(5, 117, 120),
	SIXTH(6, 120,123),
	SEVENTH(7, 123,126),
	EIGHTH(8, 126,129),
	NINTH(9, 129, 132);

	int code;
	int start;
	int end;
	
	VisitorBatOrderUtility(int code, int start, int end){
		this.code=code;
		this.start = start;
		this.end = end;
	}
	
	public static VisitorBatOrderUtility getByCode(int code) {
		for(VisitorBatOrderUtility vbo : values()) {
	        if(vbo.getCode() == code) {
	        	return vbo;
	        }
	    }
		return null;

	}

	public int getCode() {
		return code;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}

	
	

}
