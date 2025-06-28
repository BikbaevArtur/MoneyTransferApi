package ru.bikbaev.moneytransferapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferMoneyRequest {

    @Schema(description = "id пользователя, которому переводятся средства", example = "2")
    @NotNull(message = "id получателя не может быть пустым")
    private Long toUserId;

    @Schema(description = "Сумма перевода", example = "100.00")
    @NotNull(message = "Сумма перевода не может быть пустой")
    private BigDecimal amount;
}