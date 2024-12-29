package com.example.eventspherebackend.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Secure 256-bit key
    private static final long EXPIRATION_TIME = 3600000; // 1 hour in milliseconds

    // Generate a JWT token
    public String generateToken(String username,String role) {
        try {
            return Jwts.builder()
                    .setSubject(username)
                    .setSubject(role)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Use expiration constant
                    .signWith(SECRET_KEY)
                    .compact();
        } catch (Exception e) {
            e.printStackTrace();
            return "token generation failed";
        }
    }


    // Validate the JWT token
    public boolean validateToken(String token) {
        try {
            // Parse the claims from the token
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Log the error for debugging purposes (optional)
            System.out.println("JWT validation failed: " + e.getMessage());
            return false;
        }
    }

    // Get the username from the token
    public String getUsernameFromToken(String token) {
        // Parse the token and extract the subject (username)
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
