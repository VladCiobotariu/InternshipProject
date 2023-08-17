package com.ozius.internship.project.entity.seller;

import jakarta.persistence.Embeddable;

import java.time.LocalDate;

@Embeddable
public class LegalDetails {

    private String name;
    private String cui;
    private RegistrationNumber registrationNumber;

    protected LegalDetails(){

    }

    LegalDetails(String name, String cui, RegistrationNumber registrationNumber) {
        this.name = name;
        this.cui = cui;
        this.registrationNumber = registrationNumber;
    }

    public String getName() {
        return name;
    }

    public String getCui() {
        return cui;
    }

    public RegistrationNumber getRegistrationNumber() {
        return registrationNumber;
    }

    @Override
    public String toString() {
        return "LegalDetails{" +
                "name='" + name + '\'' +
                ", cui='" + cui + '\'' +
                ", registrationNumber=" + registrationNumber +
                '}';
    }
}
