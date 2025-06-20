package ru.bikbaev.moneytransferapi.service;

import ru.bikbaev.moneytransferapi.dto.request.LoginRequest;
import ru.bikbaev.moneytransferapi.dto.response.AccessToken;
import ru.bikbaev.moneytransferapi.entity.User;

public interface AuthUserService {

    AccessToken authenticate(LoginRequest request);

    User findByEmailOrPhone(String login);
}
