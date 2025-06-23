package ru.bikbaev.moneytransferapi.core.validation;

import java.math.BigDecimal;

public interface BalanceValidator {
    /**
     * Проверка, что сумма перевода больше нуля.
     * Если сумма <= 0, выбрасывается AmountMustBePositiveException.
     *
     * @param amount сумма перевода
     */
    void validateAmountTransfer(BigDecimal amount);

    /**
     * Проверка, что на счету достаточно средств для перевода.
     * Если сумма больше текущего баланса — выбрасывается InsufficientFundsException.
     *
     * @param amount сумма перевода
     * @param balance текущий баланс
     */
    void validateBalanceForTransfer(BigDecimal amount, BigDecimal balance);


    /**
     * Проверка, что пользователь не переводит средства самому себе.
     * Если fromUserId и toUserId совпадают — выбрасывается TransferToSelfException.
     *
     * @param fromUserId отправитель
     * @param toUserId получатель
     */
    void validateTransferToSelf(Long fromUserId, Long toUserId);
}
