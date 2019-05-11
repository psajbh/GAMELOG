package com.jhart.gamelog.exception;

public class DbExecutionException extends Exception{
    
    private static final long serialVersionUID = 2489811388246441135L;
    private String message;
    
    public DbExecutionException(Exception e){
        StringBuilder sb = new StringBuilder().append(e.getMessage());
        sb.append(" : " + e.getCause().getMessage());
        this.message = sb.toString();
    }
    
    public String getMessage() {
        return message;
    }


}
