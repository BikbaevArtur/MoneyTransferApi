package ru.bikbaev.moneytransferapi.core.exception;

public class PhoneAlreadyExistException extends RuntimeException{
    public PhoneAlreadyExistException(String message) {
        super(message);
    }
}
