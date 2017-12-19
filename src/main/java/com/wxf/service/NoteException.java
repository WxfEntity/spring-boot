package com.wxf.service;

public class NoteException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3724802554951858846L;

	public NoteException() {
		
	}

	public NoteException(String message) {
		super(message);
		
	}

	public NoteException(Throwable cause) {
		super(cause);
		
	}

	public NoteException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public NoteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

}
