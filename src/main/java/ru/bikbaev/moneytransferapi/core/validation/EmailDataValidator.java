package ru.bikbaev.moneytransferapi.core.validation;

import ru.bikbaev.moneytransferapi.dto.EmailUser;

public interface EmailDataValidator {

    /**
     * Проверка, что email уникален в системе.
     * Если такой email уже существует, выбрасывается EmailAlreadyExistException.
     *
     * @param email EmailUser для проверки
     */
    void validationEmailUniq(EmailUser email);

    /**
     * Проверка, что у пользователя больше одного email перед удалением.
     * Если email у пользователя всего один, выбрасывается MinimumEmailRequiredException.
     *
     * @param userId id пользователя
     */
    void validateMinimumEmailCount(Long userId);


    /**
     * Проверка на идентичность старого email и нового
     * Если они идентичны выбрасывается EmailNotChangedException
     * @param oldEmail старый email
     * @param newEmail новый email
     */
    void validateEmailChange(String oldEmail,String newEmail);

}
