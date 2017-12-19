package com.wxf.service;

public class NotebookIdNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6966144866112151069L;

	public NotebookIdNotFoundException() {
		super();
		
	}

	public NotebookIdNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public NotebookIdNotFoundException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public NotebookIdNotFoundException(String message) {
		super(message);
		
	}

	public NotebookIdNotFoundException(Throwable cause) {
		super(cause);
		
	}
	
}
