package ru.bikbaev.moneytransferapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email {
    @Schema(description = "Email адрес пользователя", example = "test0@mail.ru")
    @NotBlank(message = "Email must not be blank")
    @javax.validation.constraints.Email(message = "Email should be valid")
    private String email;
}