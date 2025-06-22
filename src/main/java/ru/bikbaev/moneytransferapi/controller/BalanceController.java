package ru.bikbaev.moneytransferapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bikbaev.moneytransferapi.core.service.BalanceService;
import ru.bikbaev.moneytransferapi.dto.response.UserBalanceResponse;

@RestController
@RequestMapping("/api/v1/balance")
public class BalanceController {
    private final BalanceService service;

    public BalanceController(BalanceService service) {
        this.service = service;
    }

    @GetMapping("/me")
    public ResponseEntity<UserBalanceResponse> getMeBalance( @RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.substring(7);
        return ResponseEntity.status(HttpStatus.OK).body(service.getUserBalance(token));
    }

}
