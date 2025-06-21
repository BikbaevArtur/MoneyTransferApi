package ru.bikbaev.moneytransferapi.validation;

import org.springframework.stereotype.Component;
import ru.bikbaev.moneytransferapi.core.validation.AccessValidator;
import ru.bikbaev.moneytransferapi.core.entity.EmailData;
import ru.bikbaev.moneytransferapi.core.entity.PhoneData;
import ru.bikbaev.moneytransferapi.core.exception.AccessDeniedException;

@Component
public class AccessValidatorImpl implements AccessValidator {
    @Override
    public void validateOwnership(Long resourceOwnerId, Long currentUserId) {
        if (!currentUserId.equals(resourceOwnerId)) {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public void validateEmailOwnership(EmailData emailData, Long userId) {
        validateOwnership(emailData.getUser().getId(), userId);
    }

    @Override
    public void validatePhoneOwnership(PhoneData phoneData, Long userId) {
        validateOwnership(phoneData.getUser().getId(), userId);
    }
}
