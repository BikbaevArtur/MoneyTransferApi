package ru.bikbaev.moneytransferapi.core.service;

import ru.bikbaev.moneytransferapi.dto.request.TransferMoneyRequest;
import ru.bikbaev.moneytransferapi.dto.response.TransferMoneyResponse;
import ru.bikbaev.moneytransferapi.dto.response.UserBalanceResponse;

public interface BalanceService {
    void accruePercentageToBalances();

    TransferMoneyResponse transferMoney(String token, TransferMoneyRequest request);

    UserBalanceResponse getUserBalance(String token);
}
