package ru.bikbaev.moneytransferapi.core.validation;

import ru.bikbaev.moneytransferapi.core.entity.PhoneData;
import ru.bikbaev.moneytransferapi.dto.PhoneNumber;

import java.util.List;

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


    /**
     * Проверка на идентичность старого номера телефона и нового
     * Если они идентичны выбрасывается PhoneNotChangedException
     *
     * @param oldPhone старый номер
     * @param newPhone новый номер
     */
    void validatePhoneChange(String oldPhone, String newPhone);


    /**
     * Проверяет список пустой или нет
     * если список пустой, выбрасывает исключение
     *
     * @param phoneData список номеров пользователя
     */
    void validatePhoneNumberIsNotEmpty(List<PhoneData> phoneData);
}
