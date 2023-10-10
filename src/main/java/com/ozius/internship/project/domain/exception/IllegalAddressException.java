package com.ozius.internship.project.domain.exception;

public class IllegalAddressException extends RuntimeException{
    public IllegalAddressException(String message) {
        super(message);
    }
}