package ru.bikbaev.moneytransferapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferMoneyRequest {
    private Long toUserId;
    private BigDecimal amount;
}
