package ru.bikbaev.moneytransferapi.core.validation;

import ru.bikbaev.moneytransferapi.dto.PhoneNumber;

public interface PhoneDataValidator {

    void validationPhoneUniq(PhoneNumber phone);

    void validateMinimumPhoneCount(Long userId);
}
