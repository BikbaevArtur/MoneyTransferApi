package ru.bikbaev.moneytransferapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bikbaev.moneytransferapi.core.service.UserService;
import ru.bikbaev.moneytransferapi.dto.request.PageableRequest;
import ru.bikbaev.moneytransferapi.dto.request.UserParamsSearch;
import ru.bikbaev.moneytransferapi.dto.response.PageResponse;
import ru.bikbaev.moneytransferapi.dto.response.UserResponseDto;
import ru.bikbaev.moneytransferapi.security.JwtService;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "User profile operations and search")
public class UserController {
    private final UserService service;
    private final JwtService jwtService;

    public UserController(UserService service, JwtService jwtService) {
        this.service = service;
        this.jwtService = jwtService;
    }

    @Operation(summary = "Search users by parameters")
    @GetMapping
    public ResponseEntity<PageResponse<UserResponseDto>> searchUser(
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

        PageableRequest pageableRequest = new PageableRequest(pageable, pageable.getPageNumber(), pageable.getPageSize());

        PageResponse<UserResponseDto> result = service.searchUsers(params, pageableRequest);

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Find user by id", description = "dateOfBirthAfter format  ")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
    }

    @Operation(summary = "Get profile of authenticated user")
    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> meProfile(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        Long userId = jwtService.extractUserId(token);
        return ResponseEntity.status(HttpStatus.OK).body(service.getUserProfile(userId));
    }
}