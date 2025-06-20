package ru.bikbaev.moneytransferapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserPhoneResponse {
    private final Long userId;
    private final String userName;
    private final Long idPhone;
    private final String phone;
}
