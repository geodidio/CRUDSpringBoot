package com.acc.app.exeption;

public class UniqueConstraintViolationException extends Exception {
    public UniqueConstraintViolationException(String message) {
        super(message);
    }

}
