package com.ozius.internship.project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = BuyerInfo.TABLE_NAME)
public class BuyerInfo {

    public static final String TABLE_NAME = "BUYER_INFO";

    interface Columns{
        String ACCOUNT_ID = "ACCOUNT_ID";
        String FIRST_NAME = "FIRST_NAME";
        String LAST_NAME = "LAST_NAME";

    }

}
