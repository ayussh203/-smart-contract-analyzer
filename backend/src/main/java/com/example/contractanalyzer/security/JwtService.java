package com.example.contractanalyzer.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;


@Service
public class JwtService {
    // Symmetric key for HS256 signing
    private Key key;
    private final long expirationMs = 1000L * 60 * 60 * 24;
    @PostConstruct
    public void init() {
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    /**
     * Builds a JWT containing username as subject and roles as a custom claim.
     *
     * @param username the user’s username
     * @param rolesCsv comma-separated roles (e.g. "ROLE_USER,ROLE_ADMIN")
     * @return signed JWT string
     */
    public String generateToken(String username, String rolesCsv) {
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", rolesCsv)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(key)
                .compact();
    }

    public Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
