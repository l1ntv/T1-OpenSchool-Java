package ru.t1.lint.springaoptask1.model.exception;

public class AccountNotFoundException extends RuntimeException {
    
    public AccountNotFoundException(String message) {
        super(message);
    }
}
