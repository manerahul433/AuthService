package com.uxpsystems.assignment.controllertests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.uxpsystems.assignment.config.AuthorizationServiceApplication;
import com.uxpsystems.assignment.external.UserProfileKafkaProxy;
import com.uxpsystems.assignment.external.UserProfileServiceProxy;
import com.uxpsystems.assignment.resourcemodel.LoginRequest;
import com.uxpsystems.assignment.service.AuthorizationService;
import com.uxpsystems.assignment.service.UserCredentialsService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthorizationServiceApplication.class)
@ActiveProfiles
@AutoConfigureMockMvc
public class AuthorizationControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AuthorizationService authorizationService;
	
	@MockBean
	private UserCredentialsService userCredentialsService;
	
	@MockBean
	private UserProfileServiceProxy profileServiceProxy;
	
	@MockBean
	private UserProfileKafkaProxy userProfileKafkaProxy;
	
	@Test
	public void performLoginTest() throws Exception {
		String token = "ABCD";
		
		Mockito.when(authorizationService.getAuthorizationToken(ArgumentMatchers.any(LoginRequest.class)))
		.thenReturn(token);
		
		this.mockMvc.perform(post("/assignments/login")
				.content("{\"username\":\"Rahul\",\"password\":\"12345\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(token));
	}

}
