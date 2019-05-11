package com.jhart.gamelog.types.utils;

public enum HomeBatOrderUtility {
    
    FIRST(1,132,135),
    SECOND(2,135, 138),
    THIRD(3, 138, 141),
    FOURTH(4, 141, 144),
    FIFTH(5, 144, 147),
    SIXTH(6, 147, 150),
    SEVENTH(7, 150,153),
    EIGHTH(8, 153,156),
    NINTH(9, 156, 159);

    int code;
    int start;
    int end;
    
    HomeBatOrderUtility(int code, int start, int end){
        this.code=code;
        this.start = start;
        this.end = end;
    }
    
    public static HomeBatOrderUtility getByCode(int code) {
        for(HomeBatOrderUtility hbo : values()) {
            if(hbo.getCode() == code) {
                return hbo;
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
