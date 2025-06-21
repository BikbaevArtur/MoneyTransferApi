package ru.bikbaev.moneytransferapi.core.exception;

public class MinimumPhoneRequiredException extends RuntimeException{
    public MinimumPhoneRequiredException(String message) {
        super(message);
    }
}
