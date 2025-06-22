package ru.bikbaev.moneytransferapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class PhoneNumber {
    private final String phoneNumber;
}
