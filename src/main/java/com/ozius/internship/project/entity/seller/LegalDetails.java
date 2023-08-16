package com.ozius.internship.project.entity.seller;

import jakarta.persistence.Embeddable;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

@Embeddable
public class LegalDetails {

    private String name;
    private String cui;
    //TODO use string to define max length for jpa?
    private String caen;
    private LocalDate dateOfEstablishment;

    protected LegalDetails(){

    }

    LegalDetails(String name, String cui, String caen, LocalDate dateOfEstablishment) {
        this.name = name;
        this.cui = cui;
        this.caen = caen;
        this.dateOfEstablishment = dateOfEstablishment;
    }

    public String getName() {
        return name;
    }

    public String getCui() {
        return cui;
    }

    public String getCaen() {
        return caen;
    }

    public LocalDate getDateOfEstablishment() {
        return dateOfEstablishment;
    }

    void updateLegalDetails(LegalDetails legalDetails){
        this.name = legalDetails.getName();
        this.cui = legalDetails.getCui();
        this.caen = legalDetails.getCaen();
        this.dateOfEstablishment = legalDetails.getDateOfEstablishment();
    }
}
