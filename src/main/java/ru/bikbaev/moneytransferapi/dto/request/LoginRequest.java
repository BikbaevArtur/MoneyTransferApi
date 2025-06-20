package ru.bikbaev.moneytransferapi.dto.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String login;
    private String password;
}
