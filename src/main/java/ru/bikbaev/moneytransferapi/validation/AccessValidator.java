package ru.bikbaev.moneytransferapi.validation;

import org.springframework.security.access.AccessDeniedException;
import ru.bikbaev.moneytransferapi.entity.EmailData;
import ru.bikbaev.moneytransferapi.entity.PhoneData;

public interface AccessValidator {
    void validateOwnership(Long resourceOwnerId, Long currentUserId);
    void validateEmailOwnership(EmailData emailData, Long userId);
    void validatePhoneOwnership(PhoneData phoneData, Long userId);


//    if (!emailData.getUser().getId().equals(userid)) {
//        throw new AccessDeniedException("You are not allowed to update this email.");
//    }
}
