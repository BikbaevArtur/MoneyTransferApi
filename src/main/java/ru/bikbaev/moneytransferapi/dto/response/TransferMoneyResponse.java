package ru.bikbaev.moneytransferapi.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
@Schema(description = "Ответ на запрос перевода средств между пользователями")
public class TransferMoneyResponse {

    @Schema(description = "Имя отправителя", example = "Test User0")
    private String fromUserName;

    @Schema(description = "Имя получателя", example = "Test User1")
    private String toUserName;

    @Schema(description = "Сумма перевода", example = "100.00")
    private BigDecimal amountTransfer;

    @Schema(description = "Текущий баланс отправителя после перевода", example = "850.00")
    private BigDecimal currentBalance;

    @Schema(description = "Дата и время перевода ", example = "2025-06-22T14:35:00")
    private String dateTime;
}