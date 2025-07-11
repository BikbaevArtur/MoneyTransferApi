package ru.bikbaev.moneytransferapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.bikbaev.moneytransferapi.core.entity.User;

@Data
@AllArgsConstructor
public class LoginUserDate {
    private String login;
    private User user;
}
