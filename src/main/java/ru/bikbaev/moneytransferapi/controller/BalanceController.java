package ru.bikbaev.moneytransferapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bikbaev.moneytransferapi.core.service.BalanceService;
import ru.bikbaev.moneytransferapi.dto.response.UserBalanceResponse;

@RestController
@RequestMapping("/api/v1/balance")
@Tag(name = "Balance", description = "Operations related to user balance")
public class BalanceController {
    private final BalanceService service;

    public BalanceController(BalanceService service) {
        this.service = service;
    }

    @Operation(summary = "Get balance of the authenticated user")
    @GetMapping("/me")
    public ResponseEntity<UserBalanceResponse> getMeBalance(@RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.substring(7);
        return ResponseEntity.status(HttpStatus.OK).body(service.getUserBalance(token));
    }
}