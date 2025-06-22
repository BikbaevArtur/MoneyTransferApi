package ru.bikbaev.moneytransferapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.bikbaev.moneytransferapi.dto.Email;
import ru.bikbaev.moneytransferapi.dto.PhoneNumber;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class UserResponseDto {
    private final Long id;
    private final String name;
    private final LocalDate dateOfBirth;
    private List<PhoneNumber> phones;
    private List<Email> emails;

}
