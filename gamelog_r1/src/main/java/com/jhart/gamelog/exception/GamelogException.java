package com.jhart.gamelog.exception;

public class GamelogException extends RuntimeException{
	
	public String message;
	
	public GamelogException() {
	}

	public GamelogException(RuntimeException re) {
		setMessage(re.getMessage());
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
