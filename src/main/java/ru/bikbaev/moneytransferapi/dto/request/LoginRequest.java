package ru.bikbaev.moneytransferapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @Schema(description = "Email(test0@mail.ru) или номер телефона (79870000000) пользователя", example = "test0@mail.ru")
    @NotBlank(message = "Логин не может быть пустым")
    private String login;

    @Schema(description = "Пароль пользователя", example = "password")
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;
}