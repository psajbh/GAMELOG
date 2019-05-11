package com.jhart.gamelog.types;

//note: originally called this enum DayOfWeek.  Not so smart as that is a Java 8 built in enum
public enum GameType {
	ZERO("0","single game"), 
	ONE("1","the first game of a double (or triple) header"), 
	TWO("2","the second game of a double (or triple) header"), 
	THREE("3","the third game of a triple-header"), 
	A("A","the first game of a double-header involving 3 teams"), 
	B("B","the second game of a double-header involving 3 teams"),
	UNK("UNK", "unknown game type");
	
	private final String code;
	private final String description;
	
	GameType(String code, String description){
		this.code = code;
		this.description = description;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public static GameType getValueFromCode(String code) {
		GameType gameType;
		switch(code) {
			case "0":
				gameType = GameType.ZERO;
				break;
			case "1":
				gameType = GameType.ONE;
				break;
			case "2":
				gameType = GameType.TWO;
				break;
			case "3":
				gameType = GameType.THREE;
				break;
			case "A":
				gameType = GameType.A;
				break;
			case "B":
				gameType = GameType.B;
				break;
			default:
				gameType = GameType.UNK;
		}
		return gameType;
	}
}
