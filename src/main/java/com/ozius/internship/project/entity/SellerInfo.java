package com.ozius.internship.project.entity;

import jakarta.persistence.*;

@Entity
@Table(name = SellerInfo.TABLE_NAME)
public class SellerInfo {

    public static final String TABLE_NAME = "SELLER_INFO";

    interface Columns{
        String ACCOUNT_ID = "ACCOUNT_ID";
        String FULL_NAME = "FULL_NAME";
        String LEGAL_ADDRESS = "LEGAL_ADDRESS";
    }

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "country", column = @Column(name = "COUNTRY")),
            @AttributeOverride( name = "state", column = @Column(name = "STATE")),
            @AttributeOverride( name = "city", column = @Column(name = "CITY")),
            @AttributeOverride( name = "addressLine1", column = @Column(name = "ADDRESS_LINE_1")),
            @AttributeOverride( name = "addressLine2", column = @Column(name = "ADDRESS_LINE_2")),
            @AttributeOverride( name = "zipCode", column = @Column(name = "ZIP_CODE"))
    })
    private Address legalAddress;

}
