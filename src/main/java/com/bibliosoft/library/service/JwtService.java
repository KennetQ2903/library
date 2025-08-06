package com.bibliosoft.library.service;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bibliosoft.library.entity.AccountEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    public String extractUsername(final String token) {
        final Claims jwtToken = Jwts.parser()
            .verifyWith(getSignInKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
        return jwtToken.getSubject();
    }

    
    public String generateToken(final AccountEntity account) {
        return buildToken(account, jwtExpiration);
    }

    public String generateRefreshToken(final AccountEntity account) {
        return buildToken(account, refreshTokenExpiration);
    }

    public String buildToken(final AccountEntity account, final long expiration) {
        return Jwts
                .builder()
                .id(account.getId().toString())
                .claims(Map.of("name", account.getUsername()))
                .subject(account.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .compact();
    }
    
    public boolean isTokenValid(final String token, final AccountEntity account) {
        final String username = extractUsername(token);
        return username.equals(account.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(final String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(final String token) {
        final Claims jwtToken = Jwts.parser()
            .verifyWith(getSignInKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
        return jwtToken.getExpiration();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
}
