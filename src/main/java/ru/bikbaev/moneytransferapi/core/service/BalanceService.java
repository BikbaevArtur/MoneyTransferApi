package ru.bikbaev.moneytransferapi.core.service;

import java.math.BigDecimal;

public interface BalanceService {
    void accruePercentageToBalances();
    void transferMoney(String token, Long toUserId, BigDecimal amount);
}
