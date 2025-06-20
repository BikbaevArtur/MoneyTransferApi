package ru.bikbaev.moneytransferapi.exception;

public class MinimumEmailRequiredException extends RuntimeException{
    public MinimumEmailRequiredException(String message) {
        super(message);
    }
}
