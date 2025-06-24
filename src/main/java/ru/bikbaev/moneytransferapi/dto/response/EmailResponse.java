package ru.bikbaev.moneytransferapi.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO ответа с email-данными пользователя")
public class EmailResponse {

    @Schema(description = "id email", example = "1")
    private Long idEmail;

    @Schema(description = "EmailUser пользователя", example = "test0@mail.ru")
    private String email;
}