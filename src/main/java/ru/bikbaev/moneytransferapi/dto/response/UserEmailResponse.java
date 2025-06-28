package ru.bikbaev.moneytransferapi.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
@Schema(description = "Ответ с информацией о пользователе и его email")
public class UserEmailResponse {

    @Schema(description = "id пользователя", example = "1")
    private final Long userId;

    @Schema(description = "Имя пользователя", example = "Test User0")
    private final String userName;

    @Schema(description = "id email", example = "1")
    private final Long emailId;

    @Schema(description = "Email пользователя", example = "test0@mail.ru")
    private final String email;
}