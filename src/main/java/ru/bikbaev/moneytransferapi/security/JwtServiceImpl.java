package ru.bikbaev.moneytransferapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class JwtServiceImpl implements JwtService {


    @Value("${spring.security.jwt.secret-key-access}")
    private String secretKey;

    @Value("${spring.security.jwt.expiration-time-access}")
    private Long expiration;

    @Override


    public String generateToken(UserDetails userDetails, Long userId) {
        log.debug("Generating token for user_id={}, username={}", userId, userDetails.getUsername());
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        return buildToken(claims, userDetails, expiration);
    }


    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        boolean valid = isUsernameValid(token, userDetails) && isTokenDateValid(token);
        log.debug("Token valid={} for username={}", valid, userDetails.getUsername());
        return  valid;
    }

    @Override
    public Long extractUserId(String token) {
        log.trace("Extracting userId from token");
        String userId = extractAllClaims(token).get("userId").toString();
        return Long.parseLong(userId);
    }

    @Override
    public String extractLogin(String token) {
        log.trace("Extracting login from token");
        return extractAllClaims(token).getSubject();
    }

    //toDo может убрать валидацию по id
    private boolean isUsernameValid(String token, UserDetails userDetails) {
        String username = extractAllClaims(token).getSubject();
        return username.equals(userDetails.getUsername());
    }

    private boolean isTokenDateValid(String token) {
        return extractDate(token).after(new Date());
    }

    private Date extractDate(String token) {
        return extractAllClaims(token).getExpiration();
    }


    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(
                        getSecretKeyAccess()
                ).build()
                .parseSignedClaims(token)
                .getPayload();
    }


    private String buildToken(
            Map<String, Object> extractClaims,
            UserDetails userDetails,
            Long expirations) {

        return Jwts
                .builder()
                .claims()
                .empty()
                .add(extractClaims)
                .and()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirations))
                .signWith(getSecretKeyAccess())
                .compact();


    }


    private SecretKey getSecretKeyAccess() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
