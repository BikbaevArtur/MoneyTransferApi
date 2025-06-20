package ru.bikbaev.moneytransferapi.validation;

import ru.bikbaev.moneytransferapi.entity.EmailData;
import ru.bikbaev.moneytransferapi.entity.PhoneData;

public interface AccessValidator {
    void validateOwnership(Long resourceOwnerId, Long currentUserId);

    void validateEmailOwnership(EmailData emailData, Long userId);

    void validatePhoneOwnership(PhoneData phoneData, Long userId);

}
