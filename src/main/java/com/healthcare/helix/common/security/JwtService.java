package com.healthcare.helix.common.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    /* Config & Secret will move into config.yml file later: TODO */
    private static final String SECRET_KEY = "my-super-secret-key-for-jwt-authentication-service-2026-very-secure-038923";
    private static final long JWT_ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 15; // 15 minutes;
    private static final long JWT_REFRESH_TOKEN_EXPIRATION = 1000L * 60 * 60 * 24 * 30; // 30 days in MS;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    };


    public String generateAccessToken(UserDetails userDetails) {
        UserPrincipal principal = (UserPrincipal) userDetails;

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("userId", principal.getUserId().toString())
                .claim("role", principal.getRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + JWT_ACCESS_TOKEN_EXPIRATION))
                .signWith(getSigningKey())
                .compact();
    }


    public String generateRefreshToken(UserDetails userDetails){
        UserPrincipal principal = (UserPrincipal) userDetails;

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("userId", principal.getUserId().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + JWT_REFRESH_TOKEN_EXPIRATION))
                .signWith(getSigningKey())
                .compact();
    }


    public String extractUsernameFromToken(String token){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }


    public boolean validateToken(String token, UserDetails userDetails) {
        String  username = extractUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public boolean validateRefreshToken(String token){
        return isTokenExpired(token);
    }


    public boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();

        return expiration.before(new Date());
    }


    public Date extractExpiration(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
    }


}
