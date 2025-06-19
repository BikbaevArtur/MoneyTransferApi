package ru.bikbaev.moneytransferapi.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserParamsSearch {
    private LocalDate dateOfBirthAfter;
    private String phone;
    private String email;
    private String namePrefix;
}
