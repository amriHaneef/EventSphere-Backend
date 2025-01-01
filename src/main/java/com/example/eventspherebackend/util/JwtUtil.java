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
import java.util.function.Function;

@Component
public class JwtUtil {

    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Secure 256-bit key
    private static final long EXPIRATION_TIME = 3600000; // 1 hour in milliseconds
    public static String role;
    public static String username;
    public static int userId;

    // Generate a JWT token
    public String generateToken(String username, String role) {
        try {
            return Jwts.builder()
                    .setSubject(username)  // Store username as the subject
                    .claim("role", role)   // Store role as a custom claim
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Use expiration constant
                    .signWith(SECRET_KEY)
                    .compact();
        } catch (Exception e) {
            e.printStackTrace();
            return "Token generation failed";
        }
    }

    // Validate the JWT token
    public boolean validateToken(String token) {
        try {
            // Parse the claims from the token
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            // Check expiration date
            return !claims.getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            // Invalid token or token expired
            return false;
        }
    }

    // Extract a specific claim from the token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extract all claims from the token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Get the username from the token
    public String getUsernameFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Get the role from the token
    public void getRoleFromToken(String token) {
        role = extractClaim(token, claims -> claims.get("role", String.class));
    }
}
