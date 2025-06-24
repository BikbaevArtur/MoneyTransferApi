package ru.bikbaev.moneytransferapi.mapper;

import ru.bikbaev.moneytransferapi.dto.EmailUser;
import ru.bikbaev.moneytransferapi.dto.response.EmailResponse;
import ru.bikbaev.moneytransferapi.dto.response.UserEmailResponse;
import ru.bikbaev.moneytransferapi.core.entity.EmailData;

import java.util.List;

public interface EmailDataMapper {
    UserEmailResponse toDto(EmailData emailData);
    List<EmailUser> allToEmail(List<EmailData> emailData);
    List<EmailResponse> allToEmailResponse(List<EmailData> emailData);
}
