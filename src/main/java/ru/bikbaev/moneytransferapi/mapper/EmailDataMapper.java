package ru.bikbaev.moneytransferapi.mapper;

import ru.bikbaev.moneytransferapi.dto.response.UserEmailResponse;
import ru.bikbaev.moneytransferapi.core.entity.EmailData;

public interface EmailDataMapper {
    UserEmailResponse toDto(EmailData emailData);
}
