package ru.bikbaev.moneytransferapi.core.exception;

public class EmailNotChangedException extends RuntimeException{
    public EmailNotChangedException(String message) {
        super(message);
    }
}
