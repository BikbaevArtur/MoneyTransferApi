package ru.bikbaev.moneytransferapi.core.exception;

public class AmountMustBePositiveException extends RuntimeException{
    public AmountMustBePositiveException(String message) {
        super(message);
    }
}
