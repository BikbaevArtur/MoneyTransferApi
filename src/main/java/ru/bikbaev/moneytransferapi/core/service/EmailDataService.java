package ru.bikbaev.moneytransferapi.core.service;

import ru.bikbaev.moneytransferapi.dto.Email;
import ru.bikbaev.moneytransferapi.dto.response.EmailResponse;
import ru.bikbaev.moneytransferapi.dto.response.UserEmailResponse;
import ru.bikbaev.moneytransferapi.core.entity.EmailData;

import java.util.List;

public interface EmailDataService {

    EmailData findByEmail(String email);

    List<Email> findAllEmailByIdUser(Long idUser);

    List<EmailResponse> getEmailUser(String token);

    UserEmailResponse addEmail(String token, Email email);

    UserEmailResponse updateEmail(String token, Long idEmail, Email newEmail);

    void deleteEmail(String token, Long idEmail);
}
