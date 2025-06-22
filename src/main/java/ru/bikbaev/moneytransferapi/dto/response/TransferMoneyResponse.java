package ru.bikbaev.moneytransferapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class TransferMoneyResponse {
    private String fromUserName;
    private String toUserName;
    private BigDecimal amountTransfer;
    private BigDecimal currentBalance;
    private String dateTime;
}
