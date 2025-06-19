package ru.bikbaev.moneytransferapi.service;

import ru.bikbaev.moneytransferapi.entity.User;

public interface AuthUserService {

    User findByEmailOrPhone(String login);
}
