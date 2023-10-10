package com.ozius.internship.project.domain.seller;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;
import java.util.Objects;

@Embeddable
public class RegistrationNumber {

    @Enumerated(EnumType.STRING)
    private CompanyType companyType;
    private int numericCodeByState;
    private int serialNumber;
    private LocalDate dateOfRegistration;

    protected RegistrationNumber() {
    }

    public RegistrationNumber(CompanyType companyType, int numericCodeByState, int serialNumber, LocalDate dateOfRegistration) {
        this.companyType = companyType;
        this.numericCodeByState = numericCodeByState;
        this.serialNumber = serialNumber;
        this.dateOfRegistration = dateOfRegistration;
    }

    public CompanyType getCompanyType() {
        return companyType;
    }

    public int getNumericCodeByState() {
        return numericCodeByState;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public LocalDate getDateOfRegistration() {
        return dateOfRegistration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistrationNumber that = (RegistrationNumber) o;
        return numericCodeByState == that.numericCodeByState && serialNumber == that.serialNumber && companyType == that.companyType && Objects.equals(dateOfRegistration, that.dateOfRegistration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyType, numericCodeByState, serialNumber, dateOfRegistration);
    }

    @Override
    public String toString() {
        return "" + companyType + numericCodeByState + "/" + serialNumber + "/" + dateOfRegistration;
    }
}
