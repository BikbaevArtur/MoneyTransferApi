package ru.bikbaev.moneytransferapi.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Информация о балансе пользователя")
public class UserBalanceResponse {

    @Schema(description = "Имя пользователя", example = "Test User0")
    private String userName;

    @Schema(description = "Текущий баланс пользователя", example = "1000.00")
    private BigDecimal balance;
}