package com.crud.project.service;

import com.crud.project.dao.CustomUserDetails;
import com.nimbusds.jwt.JWTClaimsSet;

import jakarta.servlet.http.HttpServletRequest;

public interface JwtService {

	String extractUsername(String token);

	JWTClaimsSet extractAllClaims(String token);

    String generateToken(CustomUserDetails userDetails);

    String getToken(HttpServletRequest httpServletRequest);

    String getTokenFromBearer(String bearer);

    boolean validateToken(String token);
}
