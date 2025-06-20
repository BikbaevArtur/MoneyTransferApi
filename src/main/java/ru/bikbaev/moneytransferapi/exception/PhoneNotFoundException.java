package ru.bikbaev.moneytransferapi.exception;

public class PhoneNotFoundException extends RuntimeException{
    public PhoneNotFoundException(String message) {
        super(message);
    }
}
