package ru.bikbaev.moneytransferapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBalanceResponse {
    private String userName;
    private BigDecimal balance;
}
