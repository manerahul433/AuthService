package com.uxpsystems.assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.uxpsystems.assignment.common.AuthorizationServiceException;
import com.uxpsystems.assignment.external.UserProfileKafkaProxy;
import com.uxpsystems.assignment.external.UserProfileServiceProxy;
import com.uxpsystems.assignment.resourcemodel.LoginRequest;
import com.uxpsystems.assignment.resourcemodel.Profile;
import com.uxpsystems.assignment.service.AuthorizationService;
import com.uxpsystems.assignment.service.JwtService;
import com.uxpsystems.assignment.service.UserCredentialsService;


@RestController
@RequestMapping("assignments")
public class AuthorizationController {
	
	@Autowired
	private AuthorizationService authorizationService;
	
	@Autowired
	private UserCredentialsService userCredentialsService;
	
	@Autowired
	private UserProfileServiceProxy profileServiceProxy;
	
	@Autowired
	private UserProfileKafkaProxy userProfileKafkaProxy;
	
	@RequestMapping(method = RequestMethod.POST,path = "/login")
    public @ResponseBody ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) throws AuthorizationServiceException {
		
		String token = authorizationService.getAuthorizationToken(loginRequest);
        return new ResponseEntity<String>(token,HttpStatus.OK);
    }
	
	
	@RequestMapping(method = RequestMethod.POST,path = "/profile")
    public @ResponseBody ResponseEntity<Void> createProfile(@RequestBody Profile profile, @RequestHeader(value = "token", required = false) String token ) throws AuthorizationServiceException {
		
		if(token==null || token.isEmpty()) {
			throw new AuthorizationServiceException("4002",HttpStatus.BAD_GATEWAY);
		}
		String username = authorizationService.validateAndParseJWT(token);
		
		profileServiceProxy.createProfile(username,profile);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
	
	@RequestMapping(method = RequestMethod.PUT,path = "/profile")
    public @ResponseBody ResponseEntity<Void> updateProfile(@RequestBody Profile profile, @RequestHeader(value = "token", required = false) String token ) throws AuthorizationServiceException {
		
		if(token==null || token.isEmpty()) {
			throw new AuthorizationServiceException("4002",HttpStatus.BAD_GATEWAY);
		}
		String username = authorizationService.validateAndParseJWT(token);

		userProfileKafkaProxy.updateProfile(username, profile);
        return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
    }
	
	@RequestMapping(method = RequestMethod.DELETE,path = "/profile")
    public @ResponseBody ResponseEntity<Void> deleteProfile(@RequestHeader(value = "token", required = false) String token ) throws AuthorizationServiceException {
		
		if(token==null || token.isEmpty()) {
			throw new AuthorizationServiceException("4002",HttpStatus.BAD_GATEWAY);
		}
		String username = authorizationService.validateAndParseJWT(token);
		userProfileKafkaProxy.deleteProfile(username);
        return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
    }
	
	
	
	
	
	//Just created for local testing create user flow
	@RequestMapping(method = RequestMethod.POST,path = "/createusercredentials")
    public @ResponseBody ResponseEntity<String> createUserCredentials(@RequestBody LoginRequest loginRequest) throws AuthorizationServiceException {
		
		userCredentialsService.createUserCredentials(loginRequest);
        return new ResponseEntity<String>(HttpStatus.CREATED);
    }

}
