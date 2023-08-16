package com.ozius.internship.project.entity.exeption;

public class IllegalISellerDetails extends RuntimeException{
    public IllegalISellerDetails(String message) {
        super(message);
    }
}
