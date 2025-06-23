package ru.bikbaev.moneytransferapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bikbaev.moneytransferapi.core.service.BalanceService;
import ru.bikbaev.moneytransferapi.dto.request.TransferMoneyRequest;
import ru.bikbaev.moneytransferapi.dto.response.TransferMoneyResponse;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/transfers")
@Tag(name = "Transfers", description = "Operations for money transfers")
public class TransferMoneyController {
    private final BalanceService service;

    public TransferMoneyController(BalanceService service) {
        this.service = service;
    }

    @Operation(summary = "Transfer money from authenticated user to another user")
    @PostMapping
    public ResponseEntity<TransferMoneyResponse> transferMoney(
            @Valid @RequestBody TransferMoneyRequest request,
            @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        return ResponseEntity.status(HttpStatus.OK).body(service.transferMoney(token, request));
    }
}