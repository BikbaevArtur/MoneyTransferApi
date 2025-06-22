package ru.bikbaev.moneytransferapi.core.service;

import ru.bikbaev.moneytransferapi.dto.LoginUserDate;
import ru.bikbaev.moneytransferapi.dto.request.LoginRequest;
import ru.bikbaev.moneytransferapi.dto.response.AccessTokenResponse;

public interface AuthUserService {

    AccessTokenResponse authenticate(LoginRequest request);

    LoginUserDate findByEmailOrPhone(String login);
}
