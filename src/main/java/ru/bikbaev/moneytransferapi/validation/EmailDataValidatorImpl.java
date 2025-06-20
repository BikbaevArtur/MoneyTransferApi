package ru.bikbaev.moneytransferapi.validation;

import org.springframework.stereotype.Component;
import ru.bikbaev.moneytransferapi.dto.request.Email;
import ru.bikbaev.moneytransferapi.exception.EmailAlreadyExistException;
import ru.bikbaev.moneytransferapi.exception.MinimumEmailRequiredException;
import ru.bikbaev.moneytransferapi.repository.EmailDataRepository;

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
}
