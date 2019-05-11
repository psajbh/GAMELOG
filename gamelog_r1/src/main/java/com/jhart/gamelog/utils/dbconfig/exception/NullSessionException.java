package com.jhart.gamelog.utils.dbconfig.exception;

public class NullSessionException extends Exception{

    private static final long serialVersionUID = -6844160540811387538L;
    private String message;
    
    public NullSessionException(Exception e){
        this.message = e.getMessage();
    }
    
    public NullSessionException(String message) {
        this.message = message;
    }

}
