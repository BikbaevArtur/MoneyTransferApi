package ru.bikbaev.moneytransferapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserParamsSearch {
    private LocalDate dateOfBirthAfter;
    private String phone;
    private String email;
    private String namePrefix;
}
