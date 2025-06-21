package ru.bikbaev.moneytransferapi.core.validation;

import ru.bikbaev.moneytransferapi.dto.request.Email;

public interface EmailDataValidator {

    void validationEmailUniq(Email email);

    void validateMinimumEmailCount(Long userId);

}
