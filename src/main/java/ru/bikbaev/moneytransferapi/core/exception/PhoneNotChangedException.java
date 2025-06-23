package ru.bikbaev.moneytransferapi.core.exception;

public class PhoneNotChangedException extends RuntimeException{
    public PhoneNotChangedException(String message) {
        super(message);
    }
}
