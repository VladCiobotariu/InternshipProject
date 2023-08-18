package com.ozius.internship.project.entity.seller;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;

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
    public String toString() {
        return "" + companyType + numericCodeByState + "/" + serialNumber + "/" + dateOfRegistration;
    }
}
