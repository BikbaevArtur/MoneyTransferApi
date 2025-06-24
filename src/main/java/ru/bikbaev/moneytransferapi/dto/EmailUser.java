package ru.bikbaev.moneytransferapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailUser {
    @Schema(description = "EmailUser адрес пользователя", example = "test0@mail.ru")
    @NotBlank(message = "EmailUser must not be blank")
    @Email(message = "EmailUser should be valid")
    private String email;
}