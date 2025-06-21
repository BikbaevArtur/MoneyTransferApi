package ru.bikbaev.moneytransferapi.core.exception;

public class PhoneNotFoundException extends RuntimeException{
    public PhoneNotFoundException(String message) {
        super(message);
    }
}
