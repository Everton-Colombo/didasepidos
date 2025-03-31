package com.didasepidos.didasepidoswebapp.type.exception;

public class InstitutionRatingLimitExceededException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InstitutionRatingLimitExceededException() {
		super();
	}

	public InstitutionRatingLimitExceededException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InstitutionRatingLimitExceededException(String message, Throwable cause) {
		super(message, cause);
	}

	public InstitutionRatingLimitExceededException(String message) {
		super(message);
	}

	public InstitutionRatingLimitExceededException(Throwable cause) {
		super(cause);
	}
}
