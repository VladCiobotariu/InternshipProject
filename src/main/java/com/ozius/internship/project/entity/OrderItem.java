package com.ozius.internship.project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table
public class OrderItem {

    public static final String TABLE_NAME = "ORDER_ITEM";

    interface Columns{
        String PRODUCT_ID = "PRODUCT_ID";
        String QUANTITY = "QUANTITY";
        String NAME = "NAME";
        String PRICE = "PRICE";
        String DESCRIPTION = "DESCRIPTION";
        String SELLER_ID = "SELLER_ID";
    }

}
