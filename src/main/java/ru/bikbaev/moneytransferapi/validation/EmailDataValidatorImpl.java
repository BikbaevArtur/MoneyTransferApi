package ru.bikbaev.moneytransferapi.validation;

import org.springframework.stereotype.Component;
import ru.bikbaev.moneytransferapi.core.entity.EmailData;
import ru.bikbaev.moneytransferapi.core.exception.EmailNotChangedException;
import ru.bikbaev.moneytransferapi.core.exception.EmailNotFoundException;
import ru.bikbaev.moneytransferapi.core.validation.EmailDataValidator;
import ru.bikbaev.moneytransferapi.dto.Email;
import ru.bikbaev.moneytransferapi.core.exception.EmailAlreadyExistException;
import ru.bikbaev.moneytransferapi.core.exception.MinimumEmailRequiredException;
import ru.bikbaev.moneytransferapi.repository.EmailDataRepository;

import java.util.List;

@Component
public class EmailDataValidatorImpl implements EmailDataValidator {

    private final EmailDataRepository repository;

    public EmailDataValidatorImpl(EmailDataRepository repository) {
        this.repository = repository;
    }

    @Override
    public void validationEmailUniq(Email email) {
        if (repository.existsByEmail(email.getEmail())) {
            throw new EmailAlreadyExistException("Email " + email.getEmail() + " already exists");
        }
    }

    @Override
    public void validateMinimumEmailCount(Long userId) {
        if (repository.countByUserId(userId) <= 1) {
            throw new MinimumEmailRequiredException("User must have at least one email");
        }
    }

    @Override
    public void validateEmailChange(String oldEmail, String newEmail) {
        if(newEmail.equals(oldEmail)){
            throw new EmailNotChangedException("The new email address is the same as the current one.");
        }
    }

    @Override
    public void validateEmailIsNotEmpty(List<EmailData> emailDataList) {
        if(emailDataList.isEmpty()){
            throw new EmailNotFoundException("Emails not found or user not found");
        }
    }
}
