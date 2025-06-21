package ru.bikbaev.moneytransferapi.validation;

import org.springframework.stereotype.Component;
import ru.bikbaev.moneytransferapi.core.validation.BalanceValidator;
import ru.bikbaev.moneytransferapi.core.exception.AmountMustBePositiveException;
import ru.bikbaev.moneytransferapi.core.exception.InsufficientFundsException;
import ru.bikbaev.moneytransferapi.core.exception.TransferToSelfException;

import java.math.BigDecimal;

@Component
public class BalanceValidatorImpl implements BalanceValidator {

    @Override
    public void validateAmountTransfer(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new AmountMustBePositiveException("Transfer amount must be greater than zero.");
        }
    }

    @Override
    public void validateBalanceForTransfer(BigDecimal amount, BigDecimal balance) {
        if (amount.compareTo(balance) > 0) {
            throw new InsufficientFundsException("Insufficient funds for transfer.");
        }
    }

    @Override
    public void validateTransferToSelf(Long fromUserId, Long toUserId) {
        if(fromUserId.equals(toUserId)){
            throw new TransferToSelfException("Cannot transfer money to the same account.");
        }
    }
}
