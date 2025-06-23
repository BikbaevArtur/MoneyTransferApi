package ru.bikbaev.moneytransferapi.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Ответ при успешной авторизации, содержащий JWT-токен доступа")
public class AccessTokenResponse {

    @Schema(description = "JWT токен, используемый для аутентификации", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;
}