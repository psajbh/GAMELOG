package com.jhart.gamelog.types;

public enum WeekDay {
	SUN("Sun", 1),
	MON("Mon", 2),
	TUE("Tue", 3),
	WED("Wed",4),
	THU("Thu", 5),
	FRI("Fri", 6),
	SAT("Sat",7);
	
	private String code;
	private int order;
	
	WeekDay(String code, int order){
		this.code = code;
		this.order = order;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
	
	
}
