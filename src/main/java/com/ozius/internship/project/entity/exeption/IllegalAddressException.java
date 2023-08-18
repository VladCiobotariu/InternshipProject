package com.ozius.internship.project.entity.exeption;

public class IllegalAddressException extends RuntimeException{
    public IllegalAddressException(String message) {
        super(message);
    }
}