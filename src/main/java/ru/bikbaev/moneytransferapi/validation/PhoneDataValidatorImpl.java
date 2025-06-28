package ru.bikbaev.moneytransferapi.validation;

import org.springframework.stereotype.Component;
import ru.bikbaev.moneytransferapi.core.entity.PhoneData;
import ru.bikbaev.moneytransferapi.core.exception.PhoneNotChangedException;
import ru.bikbaev.moneytransferapi.core.exception.PhoneNotFoundException;
import ru.bikbaev.moneytransferapi.core.validation.PhoneDataValidator;
import ru.bikbaev.moneytransferapi.dto.PhoneNumber;
import ru.bikbaev.moneytransferapi.core.exception.MinimumPhoneRequiredException;
import ru.bikbaev.moneytransferapi.core.exception.PhoneAlreadyExistException;
import ru.bikbaev.moneytransferapi.repository.PhoneDataRepository;

import java.util.List;

@Component
public class PhoneDataValidatorImpl implements PhoneDataValidator {
    private final PhoneDataRepository repository;

    public PhoneDataValidatorImpl(PhoneDataRepository repository) {
        this.repository = repository;
    }

    @Override
    public void validationPhoneUniq(PhoneNumber phone) {
        if (repository.existsByPhone(phone.getPhoneNumber())) {
            throw new PhoneAlreadyExistException("Phone number " + phone.getPhoneNumber() + " already exists");
        }
    }

    @Override
    public void validateMinimumPhoneCount(Long userId) {
        if (repository.countByUserId(userId) <= 1) {
            throw new MinimumPhoneRequiredException("User must have at least one phone");
        }
    }

    @Override
    public void validatePhoneChange(String oldPhone, String newPhone) {
        if(newPhone.equals(oldPhone)){
            throw new PhoneNotChangedException("The phone number is the same as the current one.");
        }
    }

    @Override
    public void validatePhoneNumberIsNotEmpty(List<PhoneData> phoneData) {
        if(phoneData.isEmpty()){
            throw new PhoneNotFoundException("Phones not found or user not found");
        }
    }
}
