package ru.bikbaev.moneytransferapi.core.service;

import ru.bikbaev.moneytransferapi.dto.request.TransferMoneyRequest;
import ru.bikbaev.moneytransferapi.dto.response.TransferMoneyResponse;

import java.math.BigDecimal;

public interface BalanceService {
    void accruePercentageToBalances();
    TransferMoneyResponse transferMoney(String token, TransferMoneyRequest request);
}
