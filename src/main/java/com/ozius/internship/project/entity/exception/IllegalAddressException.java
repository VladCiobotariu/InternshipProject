package com.ozius.internship.project.entity.exception;

public class IllegalAddressException extends RuntimeException{
    public IllegalAddressException(String message) {
        super(message);
    }
}