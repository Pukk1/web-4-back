package com.ivan.web4back.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class TokenProvider {
    private final Long tokenExpTime;
    private final String secretKey;

    public TokenProvider(
            @Value("${local.auth.token-exp-time:8000}")
            Long tokenExpTime,
            @Value("${local.auth.secret-key}")
            String secretKey
    ) {
        this.tokenExpTime = tokenExpTime;
        this.secretKey = secretKey;
    }

    private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    public String createToken(String username, String name) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + tokenExpTime);

        Map<String, String> claims = Map.of("username", username, "name", name);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        return (String) claims.get("username");
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }

}