package com.uxpsystems.assignment.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages = {"com.uxpsystems.assignment.*"})
@EnableJpaRepositories(basePackages = {"com.uxpsystems.assignment.*"})
@EntityScan(basePackages = {"com.uxpsystems.assignment.*"})
public class AuthorizationServiceApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(AuthorizationServiceApplication.class, args);
	}
	
}
