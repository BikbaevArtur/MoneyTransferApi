package ru.bikbaev.moneytransferapi.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.bikbaev.moneytransferapi.dto.ErrorResponse;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handlerAccessDenied(AccessDeniedException ex){
        ErrorResponse error = new ErrorResponse(HttpStatus.FORBIDDEN,ex.getMessage(),LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponse> handler(AccountNotFoundException ex){
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND,ex.getMessage(),LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(AmountMustBePositiveException.class)
    public ResponseEntity<ErrorResponse> handler(AmountMustBePositiveException ex){
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST,ex.getMessage(),LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handler(EmailAlreadyExistException ex){
        ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT,ex.getMessage(),LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<ErrorResponse> handler(EmailNotFoundException ex){
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND,ex.getMessage(),LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ErrorResponse> handler(InsufficientFundsException ex){
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST,ex.getMessage(),LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MinimumEmailRequiredException.class)
    public ResponseEntity<ErrorResponse> handler(MinimumEmailRequiredException ex){
        ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT,ex.getMessage(),LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(MinimumPhoneRequiredException.class)
    public ResponseEntity<ErrorResponse> handler(MinimumPhoneRequiredException ex){
        ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT,ex.getMessage(),LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(PhoneAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handler(PhoneAlreadyExistException ex){
        ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT,ex.getMessage(),LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(PhoneNotFoundException.class)
    public ResponseEntity<ErrorResponse> handler(PhoneNotFoundException ex){
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND,ex.getMessage(),LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(TransferToSelfException.class)
    public ResponseEntity<ErrorResponse> handler(TransferToSelfException ex){
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST,ex.getMessage(),LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handler(UserNotFoundException ex){
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND,ex.getMessage(),LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
