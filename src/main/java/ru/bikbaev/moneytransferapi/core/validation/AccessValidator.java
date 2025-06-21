package ru.bikbaev.moneytransferapi.core.validation;

import ru.bikbaev.moneytransferapi.core.entity.EmailData;
import ru.bikbaev.moneytransferapi.core.entity.PhoneData;

public interface AccessValidator {
    void validateOwnership(Long resourceOwnerId, Long currentUserId);

    void validateEmailOwnership(EmailData emailData, Long userId);

    void validatePhoneOwnership(PhoneData phoneData, Long userId);

}
