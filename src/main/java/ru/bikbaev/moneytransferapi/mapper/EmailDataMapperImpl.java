package ru.bikbaev.moneytransferapi.mapper;

import org.springframework.stereotype.Component;
import ru.bikbaev.moneytransferapi.dto.EmailUser;
import ru.bikbaev.moneytransferapi.dto.response.EmailResponse;
import ru.bikbaev.moneytransferapi.dto.response.UserEmailResponse;
import ru.bikbaev.moneytransferapi.core.entity.EmailData;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmailDataMapperImpl implements EmailDataMapper {

    @Override
    public UserEmailResponse toDto(EmailData emailData) {
        return UserEmailResponse.builder()
                .userId(emailData.getUser().getId())
                .userName(emailData.getUser().getName())
                .emailId(emailData.getId())
                .email(emailData.getEmail())
                .build();
    }

    @Override
    public List<EmailUser> allToEmail(List<EmailData> emailData) {
        return emailData.stream().map(e->new EmailUser(e.getEmail())).collect(Collectors.toList());
    }

    @Override
    public List<EmailResponse> allToEmailResponse(List<EmailData> emailData) {
        return emailData.stream().map(e->new EmailResponse(e.getId(),e.getEmail())).collect(Collectors.toList());
    }
}
