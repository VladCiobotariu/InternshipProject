package com.ozius.internship.project.entity.seller;

import jakarta.persistence.Embeddable;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

@Embeddable
public class LegalDetails {

    private String name;
    private String cui;
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
}
