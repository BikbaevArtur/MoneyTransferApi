package ru.bikbaev.moneytransferapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bikbaev.moneytransferapi.core.service.AuthUserService;
import ru.bikbaev.moneytransferapi.dto.request.LoginRequest;
import ru.bikbaev.moneytransferapi.dto.response.AccessTokenResponse;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthUserService service;

    public AuthController(AuthUserService service) {
        this.service = service;
    }


    @PostMapping("/login")
    public ResponseEntity<AccessTokenResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(service.authenticate(request));
    }
}
