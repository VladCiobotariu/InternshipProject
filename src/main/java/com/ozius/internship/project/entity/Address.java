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

    protected Address() {
    }

    public Address(String country, String state, String city, String addressLine1, String addressLine2, String zipCode) {
        this.country = country;
        this.state = state;
        this.city = city;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public String getZipCode() {
        return zipCode;
    }
}
