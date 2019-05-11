package com.jhart.gamelog.utils.dbconfig.exception;

public class DbConfigException extends Exception{
    
    /**
     * 
     */
    private static final long serialVersionUID = 6147051733545968525L;
    private String message;
    
    public DbConfigException(String msg){
        this.message = msg;
    }

    public DbConfigException(){
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    

}
