package ru.bikbaev.moneytransferapi.core.validation;

import ru.bikbaev.moneytransferapi.dto.Email;

public interface EmailDataValidator {

    /**
     * Проверка, что email уникален в системе.
     * Если такой email уже существует, выбрасывается EmailAlreadyExistException.
     *
     * @param email Email для проверки
     */
    void validationEmailUniq(Email email);

    /**
     * Проверка, что у пользователя больше одного email перед удалением.
     * Если email у пользователя всего один, выбрасывается MinimumEmailRequiredException.
     *
     * @param userId id пользователя
     */
    void validateMinimumEmailCount(Long userId);

}
