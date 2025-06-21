package ru.bikbaev.moneytransferapi.core.exception;

public class TransferToSelfException extends RuntimeException{
    public TransferToSelfException(String message) {
        super(message);
    }
}
