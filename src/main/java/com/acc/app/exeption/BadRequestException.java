package com.acc.app.exeption;

public class BadRequestException extends Exception {
    public BadRequestException(String message) {
        super(message);
    }
}
