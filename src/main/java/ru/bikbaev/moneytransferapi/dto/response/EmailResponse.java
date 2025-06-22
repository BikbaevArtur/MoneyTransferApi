package ru.bikbaev.moneytransferapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailResponse {
    private Long idEmail;
    private String email;
}
