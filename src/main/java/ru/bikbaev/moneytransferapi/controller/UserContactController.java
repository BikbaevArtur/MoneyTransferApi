package ru.bikbaev.moneytransferapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bikbaev.moneytransferapi.core.service.EmailDataService;
import ru.bikbaev.moneytransferapi.core.service.PhoneDataService;
import ru.bikbaev.moneytransferapi.dto.request.Email;
import ru.bikbaev.moneytransferapi.dto.request.PhoneNumber;
import ru.bikbaev.moneytransferapi.dto.response.UserEmailResponse;
import ru.bikbaev.moneytransferapi.dto.response.UserPhoneResponse;

@RestController
@RequestMapping("/api/v1/user/contacts")
public class UserContactController {
    private final EmailDataService emailService;
    private final PhoneDataService phoneService;

    public UserContactController(EmailDataService emailService, PhoneDataService phoneService) {
        this.emailService = emailService;
        this.phoneService = phoneService;
    }

    @PostMapping("/email")
    public ResponseEntity<UserEmailResponse> addEmail(@RequestBody Email email,
                                                      @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        return ResponseEntity.status(HttpStatus.CREATED).body(emailService.addEmail(token, email));
    }


    @PutMapping("/email/{id}")
    public ResponseEntity<UserEmailResponse> updateEmail(@PathVariable Long id,
                                                         @RequestBody Email email,
                                                         @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        return ResponseEntity.status(HttpStatus.OK).body(emailService.updateEmail(token, id, email));
    }


    @DeleteMapping("/email/{id}")
    public ResponseEntity<Void> deleteEmail(@PathVariable Long id,
                                            @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        emailService.deleteEmail(token,id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @PostMapping("/phone")
    public ResponseEntity<UserPhoneResponse> addPhone(@RequestBody PhoneNumber phoneNumber,
                                                      @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        return ResponseEntity.status(HttpStatus.CREATED).body(phoneService.addPhoneNumber(token,phoneNumber));
    }


    @PutMapping("/phone/{id}")
    public ResponseEntity<UserPhoneResponse> updatePhone(@PathVariable Long id,
                                                         @RequestBody PhoneNumber phoneNumber,
                                                         @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        return ResponseEntity.status(HttpStatus.OK).body(phoneService.updatePhoneNumber(token,id,phoneNumber));
    }

    @DeleteMapping("/phone/{id}")
    public ResponseEntity<Void> deletePhone(@PathVariable Long id,
                                            @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        phoneService.deletePhoneNumber(token,id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
