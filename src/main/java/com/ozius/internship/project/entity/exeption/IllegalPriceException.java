package com.ozius.internship.project.entity.exeption;

public class IllegalPriceException extends RuntimeException {
    public IllegalPriceException(String message) {
        super(message);
    }
}
