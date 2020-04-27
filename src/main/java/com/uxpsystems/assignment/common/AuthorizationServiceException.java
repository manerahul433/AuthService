package com.uxpsystems.assignment.common;

import org.springframework.http.HttpStatus;

public class AuthorizationServiceException extends Exception{
	
	private HttpStatus httpStatus;
	
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public AuthorizationServiceException(String message, HttpStatus httpStatus){
		super(message);
		this.httpStatus = httpStatus;
	}
	
	public AuthorizationServiceException(HttpStatus httpStatus){
		super();
		this.httpStatus = httpStatus;
	}

}
