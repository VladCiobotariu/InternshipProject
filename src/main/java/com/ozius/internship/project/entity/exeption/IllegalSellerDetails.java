package com.ozius.internship.project.entity.exeption;

public class IllegalSellerDetails extends RuntimeException{
    public IllegalSellerDetails(String message) {
        super(message);
    }
}
