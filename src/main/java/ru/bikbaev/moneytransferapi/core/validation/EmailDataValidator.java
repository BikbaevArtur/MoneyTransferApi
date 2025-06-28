package ru.bikbaev.moneytransferapi.core.validation;

import ru.bikbaev.moneytransferapi.core.entity.EmailData;
import ru.bikbaev.moneytransferapi.dto.Email;

import java.util.List;

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


    /**
     * Проверка на идентичность старого email и нового
     * Если они идентичны выбрасывается EmailNotChangedException
     * @param oldEmail старый email
     * @param newEmail новый email
     */
    void validateEmailChange(String oldEmail,String newEmail);


    /**
     * проверяет, список пустой или нет
     * выбрасывает исключение если список пустой
     * @param emailDataList список email пользователя
     */
    void validateEmailIsNotEmpty(List<EmailData> emailDataList);

}
