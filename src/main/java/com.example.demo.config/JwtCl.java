package com.example.demo.Config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.demo.Model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class JwtCL {

    @Value("${jwt.secret}")
    private String SECRET;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        // Ensure SECRET is at least 32 characters (256 bits) before running
        this.secretKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    // Generate a JWT token for a given User
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", user.getRoles()); // store roles in the token

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // expires in 1 day
                .signWith(secretKey, SignatureAlgorithm.HS256)  // sign with SecretKey + Algorithm
                .compact();
    }

    // Extract username (email) from JWT token
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    // Validate if token is valid for given user email
    public boolean isTokenValid(String token, String userEmail) {
        final String username = extractUsername(token);
        return (username.equals(userEmail) && !isTokenExpired(token));
    }

    // Check if the token has expired
    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    // Get Claims from the token
    private Claims getClaims(String token) {
        /*
         * Parse and verify the JWT token signature using secretKey,
         * then extract and return the claims (payload).
         *
         * Use parseClaimsJws() for compact JWT tokens signed with HS256.
         */
        return Jwts.parser()                  // create parser
                   .verifyWith(secretKey)     // verify signature with secretKey
                   .build()
                   .parseClaimsJws(token)    // parse compact Signed JWT token
                   .getBody();
    }
}
