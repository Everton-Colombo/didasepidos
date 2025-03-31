package com.didasepidos.didasepidoswebapp.type.exception;

public class InvalidReviewException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidReviewException() {}

	public InvalidReviewException(String message) {
		super(message);
	}

	public InvalidReviewException(Throwable cause) {
		super(cause);
	}

	public InvalidReviewException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidReviewException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
