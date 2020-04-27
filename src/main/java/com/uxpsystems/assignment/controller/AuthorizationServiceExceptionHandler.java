package com.uxpsystems.assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.uxpsystems.assignment.common.AuthorizationServiceException;
import com.uxpsystems.assignment.resourcemodel.CustomError;

@ControllerAdvice
@RestController
@PropertySource("classpath:errormessage.properties")
public class AuthorizationServiceExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Autowired
	private Environment environment;
	
	@ExceptionHandler(AuthorizationServiceException.class)
	public ResponseEntity<CustomError> handleAuthorizationServiceException(AuthorizationServiceException ex){
		
		CustomError customError = getPopulatedCustomError(ex.getMessage(),ex.getHttpStatus());
		return new ResponseEntity<CustomError>(customError,ex.getHttpStatus());
	}

	private CustomError getPopulatedCustomError(String message, HttpStatus status) {
		CustomError customError = new CustomError();
		if(message==null) {
			message = String.valueOf(status.value());
		}
		customError.setErrorCode(environment.getProperty(message+"_code"));
		customError.setErrorMessage(environment.getProperty(message+"_message"));
		return customError;
	}

}
