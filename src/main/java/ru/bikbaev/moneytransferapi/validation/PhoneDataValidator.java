package ru.bikbaev.moneytransferapi.validation;

import ru.bikbaev.moneytransferapi.dto.request.Email;
import ru.bikbaev.moneytransferapi.dto.request.PhoneNumber;

public interface PhoneDataValidator {

    void validationPhoneUniq(PhoneNumber phone);

    void validateMinimumPhoneCount(Long userId);
}
