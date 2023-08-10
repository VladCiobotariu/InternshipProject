package com.ozius.internship.project.entity.exeption;

public class IllegalOrderState extends RuntimeException{
    public IllegalOrderState(String message) {
        super(message);
    }
}
