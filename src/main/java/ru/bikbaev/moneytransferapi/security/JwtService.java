package ru.bikbaev.moneytransferapi.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    /**
     * Генерация JWT токена на основе данных пользователя и его ID.
     *
     * @param userDetails пользователь
     * @param userId id пользователя
     * @return токен
     */
    String generateToken(UserDetails userDetails, Long userId);

    /**
     * Проверка валидности токена по сравнению с данными пользователя.
     *
     * @param token токен
     * @param userDetails пользователь
     * @return true, если токен валиден; иначе — false
     */
    boolean isTokenValid(String token, UserDetails userDetails);


    /**
     * Извлечение id пользователя из токена.
     *
     * @param token токен
     * @return id пользователя
     */
    Long extractUserId(String token);

    /**
     * Извлечение логина пользователя (email или телефон) из токена.
     *
     * @param token  токен
     * @return логин (email или номер телефона)
     */
    String extractLogin(String token);
}
