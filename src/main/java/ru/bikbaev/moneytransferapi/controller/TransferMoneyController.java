package ru.bikbaev.moneytransferapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bikbaev.moneytransferapi.core.service.BalanceService;
import ru.bikbaev.moneytransferapi.dto.request.TransferMoneyRequest;
import ru.bikbaev.moneytransferapi.dto.response.TransferMoneyResponse;

@RestController
@RequestMapping("/api/v1/transfers")
public class TransferMoneyController {
    private final BalanceService service;

    public TransferMoneyController(BalanceService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TransferMoneyResponse> transferMoney(@RequestBody TransferMoneyRequest request, @RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.substring(7);
        return ResponseEntity.status(HttpStatus.OK).body(service.transferMoney(token,request));
    }
}
