package com.ozius.internship.project.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {

    private String country;
    private String state;
    private String city;
    private String addressLine1;
    private String addressLine2;
    private String zipCode;

}
