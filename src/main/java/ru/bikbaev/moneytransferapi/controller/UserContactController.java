package ru.bikbaev.moneytransferapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bikbaev.moneytransferapi.core.service.EmailDataService;
import ru.bikbaev.moneytransferapi.core.service.PhoneDataService;
import ru.bikbaev.moneytransferapi.dto.Email;
import ru.bikbaev.moneytransferapi.dto.PhoneNumber;
import ru.bikbaev.moneytransferapi.dto.response.EmailResponse;
import ru.bikbaev.moneytransferapi.dto.response.PhoneNumberResponse;
import ru.bikbaev.moneytransferapi.dto.response.UserEmailResponse;
import ru.bikbaev.moneytransferapi.dto.response.UserPhoneResponse;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users/contacts")
@Tag(name = "User Contacts", description = "Manage user emails and phone numbers")
public class UserContactController {
    private final EmailDataService emailService;
    private final PhoneDataService phoneService;

    public UserContactController(EmailDataService emailService, PhoneDataService phoneService) {
        this.emailService = emailService;
        this.phoneService = phoneService;
    }

    @Operation(summary = "Add email for authenticated user")
    @PostMapping("/email")
    public ResponseEntity<UserEmailResponse> addEmail(@Valid @RequestBody Email email,
                                                      @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        return ResponseEntity.status(HttpStatus.CREATED).body(emailService.addEmail(token, email));
    }

    @Operation(summary = "Get all emails by user id")
    @GetMapping("/email/user/{id}")
    public ResponseEntity<List<Email>> findByUserId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(emailService.findAllEmailByIdUser(id));
    }

    @Operation(summary = "Get emails of authenticated user")
    @GetMapping("/email/me")
    public ResponseEntity<List<EmailResponse>> getUserEmail(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        return ResponseEntity.status(HttpStatus.OK).body(emailService.getEmailUser(token));
    }

    @Operation(summary = "Update email by id for authenticated user")
    @PutMapping("/email/{id}")
    public ResponseEntity<UserEmailResponse> updateEmail(@PathVariable Long id,
                                                         @Valid @RequestBody Email email,
                                                         @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        return ResponseEntity.status(HttpStatus.OK).body(emailService.updateEmail(token, id, email));
    }

    @Operation(summary = "Delete email by id for authenticated user")
    @DeleteMapping("/email/{id}")
    public ResponseEntity<Void> deleteEmail(@Valid @PathVariable Long id,
                                            @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        emailService.deleteEmail(token, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Add phone number for authenticated user")
    @PostMapping("/phone")
    public ResponseEntity<UserPhoneResponse> addPhone(@Valid @RequestBody PhoneNumber phoneNumber,
                                                      @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        return ResponseEntity.status(HttpStatus.CREATED).body(phoneService.addPhoneNumber(token, phoneNumber));
    }

    @Operation(summary = "Get all phone numbers by user id")
    @GetMapping("/phone/user/{id}")
    public ResponseEntity<List<PhoneNumber>> findByIdUser(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(phoneService.findAllPhoneNumberByIdUser(id));
    }

    @Operation(summary = "Get phone numbers of authenticated user")
    @GetMapping("/phone/me")
    public ResponseEntity<List<PhoneNumberResponse>> getUserPhoneNumber(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        return ResponseEntity.status(HttpStatus.OK).body(phoneService.getPhoneNumberUser(token));
    }

    @Operation(summary = "Update phone number by id for authenticated user")
    @PutMapping("/phone/{id}")
    public ResponseEntity<UserPhoneResponse> updatePhone(@PathVariable Long id,
                                                         @Valid @RequestBody PhoneNumber phoneNumber,
                                                         @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        return ResponseEntity.status(HttpStatus.OK).body(phoneService.updatePhoneNumber(token, id, phoneNumber));
    }

    @Operation(summary = "Delete phone number by id for authenticated user")
    @DeleteMapping("/phone/{id}")
    public ResponseEntity<Void> deletePhone(@PathVariable Long id,
                                            @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        phoneService.deletePhoneNumber(token, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}