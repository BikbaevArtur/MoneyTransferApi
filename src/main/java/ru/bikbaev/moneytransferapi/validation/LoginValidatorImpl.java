package ru.bikbaev.moneytransferapi.validation;

import org.springframework.stereotype.Component;
import ru.bikbaev.moneytransferapi.core.exception.InvalidLoginFormatException;
import ru.bikbaev.moneytransferapi.core.validation.LoginValidator;

@Component
public class LoginValidatorImpl implements LoginValidator {

    @Override
    public void validateLogin(String login) {
        if (login == null || login.isBlank()) {
            throw new InvalidLoginFormatException("Login must not be empty");
        }
        if (login.contains("@")) {
            return;
        }
        if (!login.matches("\\d{11,13}")) {
            throw new InvalidLoginFormatException("Login must be a valid email or phone number (11-13 digits)");
        }
    }
}
