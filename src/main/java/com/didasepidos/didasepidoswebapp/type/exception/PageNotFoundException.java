package com.didasepidos.didasepidoswebapp.type.exception;

public class PageNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public PageNotFoundException() {
		super();
	}

	public PageNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PageNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public PageNotFoundException(String message) {
		super(message);
	}

	public PageNotFoundException(Throwable cause) {
		super(cause);
	}
	
	
}
