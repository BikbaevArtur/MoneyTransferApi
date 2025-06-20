package ru.bikbaev.moneytransferapi.service;

import ru.bikbaev.moneytransferapi.dto.request.Email;
import ru.bikbaev.moneytransferapi.dto.response.UserEmailResponse;
import ru.bikbaev.moneytransferapi.entity.EmailData;

public interface EmailDataService {

    EmailData findByEmail(String email);

    UserEmailResponse addEmail(String token, Email email);

    UserEmailResponse updateEmail(String token, Long idEmail, Email newEmail);

    void deleteEmail(String token, Long idEmail);
}
