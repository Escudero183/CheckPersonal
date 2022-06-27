package com.app.checkpersonal.config.exception;

public class RestException {

	private final String errorMessage;

	public RestException(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

}
