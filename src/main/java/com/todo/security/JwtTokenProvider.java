package com.todo.security;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	@Value("${app.awt.secret}")
	private String jwtSecret;
	
	@Value("${app.jwt.expiration-miliseconds}")
	private long jwtExpirationalMilliseconds;
	
	//Create Genrate token util
	
	public String genrateToken(Authentication authentication) {
	    String username = authentication.getName();
	    
	    Date currentDate = new Date();
	    
	    Date expirationDate = new Date(currentDate.getTime() + jwtExpirationalMilliseconds);
	    
	    return Jwts.builder()
	                .subject(username)
	                .issuedAt(new Date())
	                .expiration(expirationDate)
	                .signWith(key())
	                .compact();
	}
	
	public Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}
	
	//Get Username from Token
	public String getUsername(String token) {
		
		return Jwts.parser()
			.verifyWith((SecretKey) key())
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.getSubject();
			
	}
	
	//Validate token
	public Boolean validateToken(String token) {
		Jwts.parser()
		    .verifyWith((SecretKey) key())
		    .build()
		    .parse(token);
		
		return true;
	}
}
