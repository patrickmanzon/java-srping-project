package com.crud.project.controllers;

import java.util.Map;

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

import io.jsonwebtoken.Claims;

@RestController
@RequestMapping("/api")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final CustomerUserDetailsService customerUserDetailsService;
	private final JwtService jwtService;

	public AuthController(AuthenticationManager authenticationManager, CustomerUserDetailsService customerUserDetailsService, JwtService jwtService) {
		this.authenticationManager = authenticationManager;
		this.customerUserDetailsService = customerUserDetailsService;
		this.jwtService = jwtService;
	}

	@PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
		Authentication authentication =
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

		String username = authentication.getName();

		CustomUserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);

		String token = jwtService.generateToken(userDetails);

		LoginResponse loginRes = new LoginResponse(token);
		return ResponseEntity.ok(loginRes);
    }

	@GetMapping("/user")
	public Map<String, Object> getClaims(@RequestHeader (value = HttpHeaders.AUTHORIZATION) String bearer) {
	 	String token = jwtService.getTokenFromBearer(bearer);

	 	Claims userClaims = jwtService.extractAllClaims(token);
	 	System.out.println(userClaims);

	 	return userClaims;
	}
}
