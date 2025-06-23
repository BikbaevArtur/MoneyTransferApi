package ru.bikbaev.moneytransferapi.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
@Schema(description = "Ответ с информацией о пользователе и его номере телефона")
public class UserPhoneResponse {

    @Schema(description = "id пользователя", example = "101")
    private final Long userId;

    @Schema(description = "Имя пользователя", example = "Test User0")
    private final String userName;

    @Schema(description = "id телефонного номера", example = "1")
    private final Long idPhone;

    @Schema(description = "Номер телефона пользователя", example = "79870000000")
    private final String phone;
}