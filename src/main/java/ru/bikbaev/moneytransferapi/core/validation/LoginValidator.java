package ru.bikbaev.moneytransferapi.core.validation;

public interface LoginValidator {
    /**
     * Проверяет валидность логина:
     * если содержит символ @ - email
     * либо состоит только из цифр длиной от 11 до 13 -телефон
     * Выбрасывает исключение InvalidLoginFormatException
     * @param login строка логина
     */
    void validateLogin(String login);
}
