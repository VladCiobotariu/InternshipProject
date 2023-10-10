package com.ozius.internship.project.domain.seller;

import jakarta.persistence.Embeddable;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LegalDetails that = (LegalDetails) o;
        return Objects.equals(name, that.name) && Objects.equals(cui, that.cui) && Objects.equals(registrationNumber, that.registrationNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cui, registrationNumber);
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
