package com.ozius.internship.project.entity.exeption;

public class IllegalRatingException extends RuntimeException {
    public IllegalRatingException(String message) {
        super(message);
    }
}
