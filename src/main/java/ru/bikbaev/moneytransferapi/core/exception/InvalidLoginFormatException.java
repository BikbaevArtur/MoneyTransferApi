package ru.bikbaev.moneytransferapi.core.exception;

public class InvalidLoginFormatException extends RuntimeException{
    public InvalidLoginFormatException(String message) {
        super(message);
    }
}
