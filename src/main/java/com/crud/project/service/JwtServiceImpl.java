package com.crud.project.service;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.crud.project.dao.CustomUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtServiceImpl implements JwtService {
	private final String jwtSigningKey = "2b44b0b00fd822d8ce753e54dac3dc4e06c2725f7db930f3b9924468b53194dbccdbe23d7baa5ef5fbc414ca4b2e64700bad60c5a7c45eaba56880985582fba4";

	@Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public String generateToken(CustomUserDetails user) {
		HashMap<String, Object> claims = new HashMap<>();

		claims.put("roles", user.getListAuthorities());
		claims.put("client_id", user.getClientId());
		claims.put("username", user.getUsername());

        return generateToken(claims, user);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails user) {
        return Jwts.builder().setClaims(extraClaims).setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateToken(String token) {
        try {
        	Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
        	System.out.println("Invalid JWT token.");
//            log.info("Invalid JWT token.");
//            log.trace("Invalid JWT token trace: {}", e);
        } catch (ExpiredJwtException e) {
        	System.out.println("Expired JWT token");
//            log.info("Expired JWT token.");
//            log.trace("Expired JWT token trace: {}", e);
        } catch (UnsupportedJwtException e) {
        	System.out.println("Unsupported JWT token.");
//            log.info("Unsupported JWT token.");
//            log.trace("Unsupported JWT token trace: {}", e);
        } catch (IllegalArgumentException e) {
        	System.out.println("JWT token compact of handler are invalid.");
//            log.info("JWT token compact of handler are invalid.");
//            log.trace("JWT token compact of handler are invalid trace: {}", e);
        }
        return false;
    }

    public String getToken(HttpServletRequest httpServletRequest) {
        final String bearerToken = httpServletRequest.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
        	return bearerToken.substring(7,bearerToken.length());
        } // The part after "Bearer "

        return null;
   }

    public String getTokenFromBearer(String bearerToken) {

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
        	return bearerToken.substring(7,bearerToken.length());
        } // The part after "Bearer "

        return null;
   }
}
