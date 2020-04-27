package com.uxpsystems.assignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uxpsystems.assignment.common.AuthorizationServiceException;
import com.uxpsystems.assignment.dao.UserCredentialsEntity;
import com.uxpsystems.assignment.resourcemodel.LoginRequest;

@Service
public class AuthorizationService {
	
	@Autowired
	private UserCredentialsService userCredentialsService;
	
	@Autowired
	private JwtService jwtService;
	
	public String getAuthorizationToken(LoginRequest loginRequest) throws AuthorizationServiceException {

		UserCredentialsEntity userCredentialsEntity = userCredentialsService.getUserCredDetails(loginRequest.getUsername());
		String token = jwtService.createJWT(userCredentialsEntity.getUsername());
		return token;
	}

	public String validateAndParseJWT(String token) throws AuthorizationServiceException {
		return jwtService.validateAndParseJWT(token);
	}

}
