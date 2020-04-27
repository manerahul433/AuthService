package com.uxpsystems.assignment.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.uxpsystems.assignment.resourcemodel.Profile;

@Service
public class UserProfileKafkaProxy {
	
	@Value("${custom.kafka.updatetopic}")
	private String updatetopic;
	
	@Value("${custom.kafka.deletetopic}")
	private String deletetopic;
	
	@Autowired
	private KafkaTemplate<String , com.uxpsystems.assignment.external.Profile> kafkaTemplate;

	public void updateProfile(String username, Profile profile) {
		com.uxpsystems.assignment.external.Profile exProfile = 
				new com.uxpsystems.assignment.external.Profile();
		exProfile.setUsername(username);
		exProfile.setAddress(profile.getAddress());
		exProfile.setPhoneNumber(profile.getAddress());
		
		 kafkaTemplate.send(updatetopic, exProfile);
	}
	
	public void deleteProfile(String username) {
		com.uxpsystems.assignment.external.Profile exProfile = 
				new com.uxpsystems.assignment.external.Profile();
		exProfile.setUsername(username);
		
		 kafkaTemplate.send(deletetopic, exProfile);
	}
}
