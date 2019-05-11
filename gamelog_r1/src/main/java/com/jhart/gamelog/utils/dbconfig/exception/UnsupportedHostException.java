package com.jhart.gamelog.utils.dbconfig.exception;

	public class UnsupportedHostException extends Exception{
	    
		private String message;
	    
	    public UnsupportedHostException(Exception e){
	        this.message = e.getMessage();
	    }


}
