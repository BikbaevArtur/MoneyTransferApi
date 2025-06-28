package ru.bikbaev.moneytransferapi.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.bikbaev.moneytransferapi.dto.Email;
import ru.bikbaev.moneytransferapi.dto.PhoneNumber;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Ответ с информацией о пользователе, включая телефоны и email")
public class UserResponseDto implements Serializable {

    @Schema(description = "id пользователя", example = "1")
    private Long id;

    @Schema(description = "Имя пользователя", example = "Test User0")
    private String name;

    @Schema(description = "Дата рождения пользователя", example = "2000-01-01")
    private LocalDate dateOfBirth;

    @Schema(description = "Список телефонов пользователя")
    private List<PhoneNumber> phones;

    @Schema(description = "Список email пользователя")
    private List<Email> emails;
}