package ru.bikbaev.moneytransferapi.service;

import ru.bikbaev.moneytransferapi.entity.User;

public interface UserService {

     User findEntityById(Long id);
}
