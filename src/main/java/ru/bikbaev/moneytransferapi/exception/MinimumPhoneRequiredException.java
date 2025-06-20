package ru.bikbaev.moneytransferapi.exception;

public class MinimumPhoneRequiredException extends RuntimeException{
    public MinimumPhoneRequiredException(String message) {
        super(message);
    }
}
