package ru.bikbaev.moneytransferapi.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.bikbaev.moneytransferapi.dto.Email;
import ru.bikbaev.moneytransferapi.dto.PhoneNumber;

import java.time.LocalDate;
import java.util.List;


@Data
@AllArgsConstructor
@Builder
@Schema(description = "Ответ с информацией о пользователе, включая телефоны и email")
public class UserResponseDto {

    @Schema(description = "id пользователя", example = "1")
    private final Long id;

    @Schema(description = "Имя пользователя", example = "Test User0")
    private final String name;

    @Schema(description = "Дата рождения пользователя", example = "2000-01-01")
    private final LocalDate dateOfBirth;

    @Schema(description = "Список телефонов пользователя")
    private final List<PhoneNumber> phones;

    @Schema(description = "Список email пользователя")
    private final List<Email> emails;
}