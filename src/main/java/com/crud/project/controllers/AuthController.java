package com.crud.project.controllers;

import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crud.project.dao.CustomUserDetails;
import com.crud.project.dao.LoginRequest;
import com.crud.project.dao.LoginResponse;
import com.crud.project.service.CustomerUserDetailsService;
import com.crud.project.service.JwtService;
import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jose.util.Base64URL;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;

import io.jsonwebtoken.Claims;

@RestController
@RequestMapping("/api")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final CustomerUserDetailsService customerUserDetailsService;
	private final JwtService jwtService;

	public AuthController(AuthenticationManager authenticationManager, CustomerUserDetailsService customerUserDetailsService,
						JwtService jwtService) {
		this.authenticationManager = authenticationManager;
		this.customerUserDetailsService = customerUserDetailsService;
		this.jwtService = jwtService;
	}

	@PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) throws JOSEException, ParseException {
		Authentication authentication =
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

		String username = authentication.getName();

		CustomUserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);

		String token = jwtService.generateToken(userDetails);

		LoginResponse loginRes = new LoginResponse(token);
		return ResponseEntity.ok(loginRes);
    }

	@GetMapping("/user")
	public Map<String, Object> getClaims(@RequestHeader (value = HttpHeaders.AUTHORIZATION) String bearer) throws ParseException {
	 	String token = jwtService.getTokenFromBearer(bearer);

	 	JWTClaimsSet userClaims = jwtService.extractAllClaims(token);

	 	return userClaims.getClaims();
	}
}
