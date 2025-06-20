package ru.bikbaev.moneytransferapi.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(UserDetails userDetails, Long userId);
    boolean isTokenValid(String token, UserDetails userDetails);
    Long extractUserId(String token);
    String extractLogin(String token);
}
