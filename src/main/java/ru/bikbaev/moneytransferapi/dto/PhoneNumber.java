package ru.bikbaev.moneytransferapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Schema(description = "DTO для номера телефона пользователя")
public class PhoneNumber {
    @Schema(description = "Телефонный номер без знака +, 11-13 цифр, начинается с 7", example = "79870000000")
    @NotBlank(message = "Phone number must not be blank")
    @Pattern(regexp = "^7\\d{10,12}$", message = "Phone number must start with 7 and contain from 11 to 13 digits")
    private final String phoneNumber;
}