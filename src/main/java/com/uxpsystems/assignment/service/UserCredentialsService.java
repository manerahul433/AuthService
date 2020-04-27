package com.uxpsystems.assignment.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.uxpsystems.assignment.common.AuthorizationServiceException;
import com.uxpsystems.assignment.dao.UserCredentialsEntity;
import com.uxpsystems.assignment.dao.UserCredentialsRepository;
import com.uxpsystems.assignment.resourcemodel.LoginRequest;

@Service
public class UserCredentialsService {
	
	@Autowired
	private UserCredentialsRepository userCredentialsRepository;

	public UserCredentialsEntity getUserCredDetails(String username) throws AuthorizationServiceException {
		UserCredentialsEntity userCredentialsEntity;
		Optional<UserCredentialsEntity> userCredentialsEntityOptional = 
				userCredentialsRepository.findById(username);
		
		if(userCredentialsEntityOptional.isPresent()) {
			userCredentialsEntity = userCredentialsEntityOptional.get();
		}else {
			//throw user Not found exception
			throw new AuthorizationServiceException("4001",HttpStatus.NOT_FOUND);
		}
		return userCredentialsEntity;
	}

	public void createUserCredentials(LoginRequest loginRequest) {
		UserCredentialsEntity userCredentialsEntity = new UserCredentialsEntity();
		userCredentialsEntity.setUsername(loginRequest.getUsername());
		userCredentialsEntity.setPassword(loginRequest.getPassword());
		userCredentialsRepository.save(userCredentialsEntity);
		
	}

}
