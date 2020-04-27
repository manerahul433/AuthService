package com.uxpsystems.assignment.external;

import javax.security.sasl.AuthenticationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.uxpsystems.assignment.common.AuthorizationServiceException;
import com.uxpsystems.assignment.resourcemodel.Profile;

@Service
public class UserProfileServiceProxy {
	
	Logger logger = LoggerFactory.getLogger(UserProfileServiceProxy.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${custom.userprofileService.url}")
	private String url;

	public void createProfile(String username, Profile profile) throws AuthorizationServiceException {
		com.uxpsystems.assignment.external.Profile exProfile = 
				new com.uxpsystems.assignment.external.Profile();
		exProfile.setUsername(username);
		exProfile.setAddress(profile.getAddress());
		exProfile.setPhoneNumber(profile.getAddress());
		
		HttpEntity<com.uxpsystems.assignment.external.Profile> request = new HttpEntity<>(exProfile);
		
		try {
			restTemplate.postForEntity(url, request, Void.class);
		}catch(RestClientException exception) {
			logger.error("Exception while connecting to POST profile operation",exception);
			throw new AuthorizationServiceException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
