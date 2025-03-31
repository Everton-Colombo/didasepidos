package com.didasepidos.didasepidoswebapp.type.exception;

public class AuthorIdGenerationRequestLimitExceededException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AuthorIdGenerationRequestLimitExceededException() {
		super();
	}

	public AuthorIdGenerationRequestLimitExceededException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AuthorIdGenerationRequestLimitExceededException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthorIdGenerationRequestLimitExceededException(String message) {
		super(message);
	}

	public AuthorIdGenerationRequestLimitExceededException(Throwable cause) {
		super(cause);
	}
}
