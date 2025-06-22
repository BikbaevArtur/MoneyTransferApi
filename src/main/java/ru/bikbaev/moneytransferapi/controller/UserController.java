package ru.bikbaev.moneytransferapi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bikbaev.moneytransferapi.core.service.UserService;
import ru.bikbaev.moneytransferapi.dto.request.UserParamsSearch;
import ru.bikbaev.moneytransferapi.dto.response.UserResponseDto;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }


    @GetMapping
    public ResponseEntity<Page<UserResponseDto>> searchUser(
            @RequestParam(required = false) String dateOfBirthAfter,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String namePrefix,
            Pageable pageable) {
        UserParamsSearch params = new UserParamsSearch();


        if (dateOfBirthAfter != null) {
            params.setDateOfBirthAfter(LocalDate.parse(dateOfBirthAfter));
        }

        params.setPhone(phone);
        params.setEmail(email);
        params.setNamePrefix(namePrefix);

        Page<UserResponseDto> result = service.searchUsers(params, pageable);
        return ResponseEntity.ok(result);
    }


}
