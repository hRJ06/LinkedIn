package com.Hindol.LinkedIn.API_Gateway.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class JWTService {
    @Value("${jwt.secret_key}")
    private String jwt_secret_key;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwt_secret_key.getBytes(StandardCharsets.UTF_8));
    }


    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }
}