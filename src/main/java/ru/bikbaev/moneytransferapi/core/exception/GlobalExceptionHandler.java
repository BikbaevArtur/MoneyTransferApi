package ru.bikbaev.moneytransferapi.core.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.bikbaev.moneytransferapi.dto.ErrorResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice

public class GlobalExceptionHandler {

    private String getDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handlerAccessDenied(Exception ex) {
        log.error("Unexpected error: ", ex);
        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Внутренняя ошибка сервера",
                getDateTime());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();

        exception
                .getBindingResult()
                .getAllErrors()
                .forEach(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });
        log.warn("Validation failed: {} | Errors: {}", exception.getMessage(), errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.UNAUTHORIZED,
                "Неверный логин или пароль",
                getDateTime()
        );
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(error);
    }

    @ExceptionHandler(InvalidLoginFormatException.class)
    public ResponseEntity<ErrorResponse> handleInvalidLoginFormatException(InvalidLoginFormatException ex) {
        log.warn("Invalid login format: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                getDateTime()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handlerAccessDenied(AccessDeniedException ex) {
        log.error("AccessDeniedException: ", ex);
        ErrorResponse error = new ErrorResponse(
                HttpStatus.FORBIDDEN,
                ex.getMessage(),
                getDateTime());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerAccountNotFoundException(AccountNotFoundException ex) {
        log.warn("AccountNotFoundException: ", ex);
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                getDateTime());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(AmountMustBePositiveException.class)
    public ResponseEntity<ErrorResponse> handlerAmountMustBePositiveException(AmountMustBePositiveException ex) {
        log.error("AmountMustBePositiveException: ", ex);
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                getDateTime());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handlerEmailAlreadyExistException(EmailAlreadyExistException ex) {
        log.warn("EmailAlreadyExistException: ", ex);
        ErrorResponse error = new ErrorResponse(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                getDateTime());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(EmailNotChangedException.class)
    public ResponseEntity<ErrorResponse> handleEmailNotChangedException(EmailNotChangedException ex) {
        log.warn("EmailUser update failed: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                getDateTime());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerEmailNotFoundException(EmailNotFoundException ex) {
        log.warn("EmailNotFoundException: ", ex);
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                getDateTime());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ErrorResponse> handlerInsufficientFundsException(InsufficientFundsException ex) {
        log.warn("InsufficientFundsException: ", ex);
        ErrorResponse error = new ErrorResponse(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                getDateTime());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(MinimumEmailRequiredException.class)
    public ResponseEntity<ErrorResponse> handlerMinimumEmailRequiredException(MinimumEmailRequiredException ex) {
        log.warn("MinimumEmailRequiredException: ", ex);
        ErrorResponse error = new ErrorResponse(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                getDateTime());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(MinimumPhoneRequiredException.class)
    public ResponseEntity<ErrorResponse> handlerMinimumPhoneRequiredException(MinimumPhoneRequiredException ex) {
        log.warn("MinimumPhoneRequiredException: ", ex);
        ErrorResponse error = new ErrorResponse(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                getDateTime());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(PhoneAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handlerPhoneAlreadyExistException(PhoneAlreadyExistException ex) {
        log.warn("PhoneAlreadyExistException: ", ex);
        ErrorResponse error = new ErrorResponse(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                getDateTime());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }


    @ExceptionHandler(PhoneNotChangedException.class)
    public ResponseEntity<ErrorResponse> handlePhoneNotChangedException(PhoneNotChangedException ex) {
        log.warn("Phone update failed: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                getDateTime());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }


    @ExceptionHandler(PhoneNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerPhoneNotFoundException(PhoneNotFoundException ex) {
        log.warn("PhoneNotFoundException: ", ex);
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                getDateTime());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(TransferToSelfException.class)
    public ResponseEntity<ErrorResponse> handlerTransferToSelfException(TransferToSelfException ex) {
        log.warn("TransferToSelfException: ", ex);
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                getDateTime());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerUserNotFoundException(UserNotFoundException ex) {
        log.warn("UserNotFoundException: ", ex);
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                getDateTime());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
