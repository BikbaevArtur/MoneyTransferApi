package ru.bikbaev.moneytransferapi.service;

import ru.bikbaev.moneytransferapi.dto.LoginUserDate;
import ru.bikbaev.moneytransferapi.dto.request.LoginRequest;
import ru.bikbaev.moneytransferapi.dto.response.AccessToken;

public interface AuthUserService {

    AccessToken authenticate(LoginRequest request);

    LoginUserDate findByEmailOrPhone(String login);
}
