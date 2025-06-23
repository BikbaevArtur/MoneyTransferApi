package ru.bikbaev.moneytransferapi.core.validation;

import ru.bikbaev.moneytransferapi.dto.PhoneNumber;

public interface PhoneDataValidator {

    /**
     * Проверка, что телефон уникален в системе.
     * Если телефон уже существует, выбрасывается PhoneAlreadyExistException.
     *
     * @param phone объект с номером телефона для проверки
     */
    void validationPhoneUniq(PhoneNumber phone);

    /**
     * Проверка, что у пользователя больше одного номера телефона перед удалением.
     * Если номер один или отсутствует, выбрасывается MinimumPhoneRequiredException.
     *
     * @param userId id пользователя
     */
    void validateMinimumPhoneCount(Long userId);
}
