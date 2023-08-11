package com.ozius.internship.project.entity.exeption;

public class InvalidCartItemQuantity extends RuntimeException{
    public InvalidCartItemQuantity(String message) {
        super(message);
    }
}
