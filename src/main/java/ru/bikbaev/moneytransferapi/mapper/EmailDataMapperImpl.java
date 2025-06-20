package ru.bikbaev.moneytransferapi.mapper;

import org.springframework.stereotype.Component;
import ru.bikbaev.moneytransferapi.dto.response.UserEmailResponse;
import ru.bikbaev.moneytransferapi.entity.EmailData;

@Component
public class EmailDataMapperImpl implements EmailDataMapper{

    @Override
    public UserEmailResponse toDto(EmailData emailData) {
        return UserEmailResponse.builder()
                .userId(emailData.getUser().getId())
                .userName(emailData.getUser().getName())
                .emailId(emailData.getId())
                .email(emailData.getEmail())
                .build();
    }
}
