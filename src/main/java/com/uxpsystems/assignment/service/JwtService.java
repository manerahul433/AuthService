package com.uxpsystems.assignment.service;

import java.util.Base64;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Payload;
import com.google.gson.Gson;
import com.uxpsystems.assignment.common.AuthorizationServiceException;
import com.uxpsystems.assignment.dto.JWTPayLoad;

@Service
public class JwtService {
	
	Logger logger = LoggerFactory.getLogger(JwtService.class);
	
	@Value("${custom.jwt.subject}")
	private String subject;
	
	@Value("${custom.jwt.issuer}")
	private String issuer;
	
	//this input to algorithm is secrete key and should be secured as we secure the Database passwords
	@Value("${custom.jwt.secrete}")
	private String secrete;
	
	@Value("${custom.jwt.ttlMillis}")
	private String ttlMillis;
	
	//Sample method to construct a JWT
	public String createJWT(String userId) {

	    //The JWT signature algorithm we will be using to sign the token
	    Algorithm signatureAlgorithm = Algorithm.HMAC256(secrete);

	    long nowMillis = System.currentTimeMillis();
	    Date now = new Date(nowMillis);
	    
	  //if it has been specified, let's add the expiration
	    long expMillis = nowMillis + Long.valueOf(ttlMillis);
	    Date expiresAt = new Date(expMillis);

	    //Let's set the JWT Claims
	    String jwt =JWT.create().withIssuedAt(now)
	                                .withSubject(subject)
	                                .withIssuer(issuer)
	                                .withExpiresAt(expiresAt)
	                                .withClaim("userId", userId.toString())
	                                .sign(signatureAlgorithm);
	    return jwt;
	}

	//Sample method to validate and read the JWT
	public String validateAndParseJWT(String token) throws AuthorizationServiceException {
	 
		//The JWT signature algorithm we will be using to sign the token
	    Algorithm signatureAlgorithm = Algorithm.HMAC256(secrete);
	    JWTVerifier jwtVerifier = JWT.require(signatureAlgorithm).withIssuer(issuer).build();
	    DecodedJWT decodedJwt = null;
		try {
			decodedJwt = jwtVerifier.verify(token);
		} catch(JWTVerificationException ex) {
			logger.error("Exception while verifying token",ex);
			throw new AuthorizationServiceException("4003",HttpStatus.UNAUTHORIZED);
		}
	    /**
	     * It throws below exceptions for better exception handling we can catch each of them
	     * 
	     * * @throws AlgorithmMismatchException     if the algorithm stated in the token's header it's not equal to the one defined in the {@link JWTVerifier}.
	     * @throws SignatureVerificationException if the signature is invalid.
	     * @throws TokenExpiredException          if the token has expired.
	     * @throws InvalidClaimException          if a claim contained a different value than the expected one.
	     */
	    Gson gson = new Gson();
	    JWTPayLoad payload = gson.fromJson(new String(Base64.getMimeDecoder().decode(decodedJwt.getPayload())), JWTPayLoad.class);	
	    return payload.getUserId();
	}

}
