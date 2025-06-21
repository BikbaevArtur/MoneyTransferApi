package ru.bikbaev.moneytransferapi.core.exception;

public class MinimumEmailRequiredException extends RuntimeException{
    public MinimumEmailRequiredException(String message) {
        super(message);
    }
}
