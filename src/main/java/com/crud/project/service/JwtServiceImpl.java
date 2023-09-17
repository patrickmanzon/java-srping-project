package com.crud.project.service;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.crud.project.dao.CustomUserDetails;
import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jose.util.Base64URL;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTClaimsSet.Builder;

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
	private final String jwtSigningKey = "Tv8YKEtNorZj3KmGAMW5+xZxMMgAWz72YAyl+nyX9FQ=";
    private final String key;

    public JwtServiceImpl(@Value("${sample.secret-value}") String key) {
        this.key = key;
    }

	@Override
    public String extractUsername(String token) {
        return extractClaim(token, claim -> claim.getSubject());
    }

    @Override
    public String generateToken(CustomUserDetails user) {
		HashMap<String, Object> claims = new HashMap<>();

		claims.put("roles", user.getListAuthorities());
		claims.put("client_id", user.getClientId());
		claims.put("username", user.getUsername());

		return generateToken(claims, user);
//        return generateToken(claims, user);
    }

    private <T> T extractClaim(String token, Function<JWTClaimsSet, T> claimsResolvers) {
        final JWTClaimsSet claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private String generateToken(HashMap<String, Object> extraClaims, UserDetails user) {
   	    try {
            // RSAEncrypter encrypter = new RSAEncrypter(rsaKey().toRSAPublicKey());

            Builder claimsSetBuilder = new JWTClaimsSet.Builder();
            claimsSetBuilder.subject(user.getUsername());
            claimsSetBuilder.expirationTime(new Date(System.currentTimeMillis() + 1000 * 60 * 24));

            for (Map.Entry<String, Object> claim: extraClaims.entrySet()) {
                claimsSetBuilder.claim(claim.getKey(), claim.getValue());
            }

            JWTClaimsSet claimsSet = claimsSetBuilder.build();

            JWEObject jweObject = new JWEObject(generateHeader(), claimsSet.toPayload());
            jweObject.encrypt(new DirectEncrypter(secretKey()));
            // EncryptedJWT jwt = new EncryptedJWT(generateHeader(), claimsSet);
		    // jwt.encrypt(encrypter);

            // return jwt.serialize();
            return jweObject.serialize();
        } catch (JOSEException e) {
            e.printStackTrace();
        }

        return null;
        // return Jwts.builder().setClaims(extraClaims).setSubject(user.getUsername())
        //         .setIssuedAt(new Date(System.currentTimeMillis()))
        //         .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
        //         .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    public JWTClaimsSet extractAllClaims(String token) {
		try {
            // EncryptedJWT jwt = EncryptedJWT.parse(token);
            // RSADecrypter decrypter = new RSADecrypter(rsaKey().toPrivateKey());
            // jwt.decrypt(decrypter);

            EncryptedJWT jwt = EncryptedJWT.parse(token);
            jwt.decrypt(new DirectDecrypter(secretKey()));

            Payload payload = jwt.getPayload();

            return jwt.getJWTClaimsSet();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (KeyLengthException e) {
            e.printStackTrace();
        } catch (JOSEException e) {
            e.printStackTrace();
        }

        return null;
    }

    private JWEHeader generateHeader() {
    	// return new JWEHeader(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A256GCM);
        return new JWEHeader(JWEAlgorithm.DIR, EncryptionMethod.A128CBC_HS256);
    }

    // private Key getSigningKey() {
    //     byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
    //     return Keys.hmacShaKeyFor(keyBytes);
    // }

    public boolean validateToken(String token) {
        try {
            // EncryptedJWT jwt = EncryptedJWT.parse(token);
            // RSADecrypter decrypter = new RSADecrypter(rsaKey().toPrivateKey());

            // jwt.decrypt(decrypter);
            EncryptedJWT jwt = EncryptedJWT.parse(token);
            jwt.decrypt(new DirectDecrypter(secretKey()));

            return true;
        } catch (JOSEException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
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

   private SecretKey secretKey() {
        byte[] decodedKey = Base64.getDecoder().decode(key);
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        return originalKey;
   }

    // private RSAKey rsaKey() {
    //     RSAPrivateCrtKey crtKey = (RSAPrivateCrtKey) this.key;
    //     Base64URL n = Base64URL.encode(crtKey.getModulus());
    //     Base64URL e = Base64URL.encode(crtKey.getPublicExponent());
    //     return new RSAKey.Builder(n, e)
    //             .privateKey(this.key)
    //             .keyUse(KeyUse.ENCRYPTION)
    //             .build();
    // }
}
