package ru.t1.lint.springaoptask2.model.exception;

public class ClientNotFoundException extends RuntimeException {

    public ClientNotFoundException(String message) {
        super(message);
    }
}
