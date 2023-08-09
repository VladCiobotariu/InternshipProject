package com.ozius.internship.project.entity.exeption;

public class OrderAlreadyProcessedException extends RuntimeException{
    public OrderAlreadyProcessedException(String message) {
        super(message);
    }
}
