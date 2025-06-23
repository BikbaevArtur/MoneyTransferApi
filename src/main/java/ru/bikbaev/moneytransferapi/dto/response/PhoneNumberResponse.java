package ru.bikbaev.moneytransferapi.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO ответа с номером телефона пользователя")
public class PhoneNumberResponse {

    @Schema(description = "id номера телефона", example = "1")
    private Long idPhoneNumber;

    @Schema(description = "Номер телефона пользователя", example = "79870000000")
    private String phoneNumber;
}