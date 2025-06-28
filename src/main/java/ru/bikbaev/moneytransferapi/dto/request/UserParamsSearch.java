package ru.bikbaev.moneytransferapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Параметры фильтрации пользователей")
public class UserParamsSearch {

    @Schema(
            description = "Дата рождения — искать пользователей, родившихся после этой даты",
            example = "2000-01-01"
    )
    private LocalDate dateOfBirthAfter;

    @Schema(
            description = "Телефон пользователя (полное совподение)",
            example = "79870000000"
    )
    private String phone;

    @Schema(
            description = "Email пользователя (полное совподение)",
            example = "test0@mail.ru"
    )
    private String email;

    @Schema(
            description = "Фильтрация по префиксу имени пользователя",
            example = "Аh"
    )
    private String namePrefix;
}