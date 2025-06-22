package ru.bikbaev.moneytransferapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneNumberResponse {
    private Long idPhoneNumber;
    private String phoneNumber;
}
