package com.example.demo.security;

import java.security.Key;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	@Value("${app.jwt-secret}")
	private String jwtSecret;
	
	@Value("${app.jwt-expiration}")
	private long jwtExpiration;
	
	
	public String generateToken(Authentication authentication) {
		String userName = authentication.getName();
		Date currentDate = new Date();
		Date expiryDate = new Date(currentDate.getTime() + jwtExpiration);
		return Jwts.builder()
				.setSubject(userName)
				.setIssuedAt(currentDate)
				.setExpiration(expiryDate)
				.signWith(key())
				.compact();
	}
	
	private Key key() {
		byte [] bytes = Decoders.BASE64.decode(jwtSecret);
		return Keys.hmacShaKeyFor(bytes);
	}
	
	public String getUserName(String token) {
		Claims claims = Jwts.parser()
				.setSigningKey(key())
				.build()
				.parseClaimsJws(token)
				.getBody();
		return claims.getSubject();
	}
	
	public boolean validateToken(String token) throws Exception {
		try {
			Jwts.parser().setSigningKey(key())
			.build()
			.parse(token);
			return true;
		}
		catch(Exception e) {
			throw new Exception();
		}
	}
	
}
