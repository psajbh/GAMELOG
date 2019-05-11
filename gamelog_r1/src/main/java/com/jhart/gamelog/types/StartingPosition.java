package com.jhart.gamelog.types;

//import com.jhart.gamelog.types.utils.VisitorBatOrderUtility;

public enum StartingPosition {
	M("0", "Manager"),
	P("1","Pitcher"),
	C("2","Catcher"),
	FB("3","FirstBase"),
	SB("4","SecondBase"),
	TB("5","ThirdBase"),
	SS("6","ShortStop"),
	LF("7","LeftField"),
	CF("8","CenterField"),
	RF("9","RightField"),
	DH("10","DesignatedHitter");
	
    private String code;
    private String description;
    
    StartingPosition(String code, String description){
        this.code = code;
        this.description = description;
    }
    
    public static StartingPosition getByCode(String code) {
        for(StartingPosition startingPosition : values()) {
            if(startingPosition.getCode().equals(code)) {
                return startingPosition;
            }
        }
        return null;

    }
    

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
    

}
