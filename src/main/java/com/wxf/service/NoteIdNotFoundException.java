package com.wxf.service;

public class NoteIdNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8714069167638495180L;

	public NoteIdNotFoundException() {
		
	}

	public NoteIdNotFoundException(String message) {
		super(message);
		
	}

	public NoteIdNotFoundException(Throwable cause) {
		super(cause);
		
	}

	public NoteIdNotFoundException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public NoteIdNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

}
