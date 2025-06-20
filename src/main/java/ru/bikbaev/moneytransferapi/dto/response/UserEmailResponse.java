package ru.bikbaev.moneytransferapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserEmailResponse {
    private final Long userId;
    private final String userName;
    private final Long emailId;
    private final String email;
}
