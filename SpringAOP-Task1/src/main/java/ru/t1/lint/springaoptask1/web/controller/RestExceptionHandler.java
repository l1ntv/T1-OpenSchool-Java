package ru.t1.lint.springaoptask1.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.t1.lint.springaoptask1.model.exception.*;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ExceptionBody> accountNotFound(AccountNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionBody(e.getMessage()));
    }

    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<ExceptionBody> transactionNotFound(Exception e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionBody(e.getMessage()));
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ExceptionBody> clientNotFound(ResourceConflictException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionBody(e.getMessage()));
    }

    @ExceptionHandler(ResourceConflictException.class)
    public ResponseEntity<ExceptionBody> resourceConflict(ResourceConflictException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ExceptionBody(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionBody> requestDataNotValid(MethodArgumentNotValidException e) {
        StringBuilder builder = new StringBuilder();
        for (ObjectError error : e.getAllErrors()) {
            builder.append(error.getDefaultMessage())
                    .append(" ");
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionBody(builder.toString()));
    }
}
