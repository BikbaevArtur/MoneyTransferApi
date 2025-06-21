package ru.bikbaev.moneytransferapi.core.validation;

import java.math.BigDecimal;

public interface BalanceValidator {
    void validateAmountTransfer(BigDecimal amount);

    void validateBalanceForTransfer(BigDecimal amount, BigDecimal balance);

    void validateTransferToSelf(Long fromUserId, Long toUserId);
}
