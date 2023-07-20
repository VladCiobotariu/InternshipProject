package com.ozius.internship.project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = CartItem.TABLE_NAME)
public class CartItem extends BaseEntity {
    public static final String TABLE_NAME = "cart_item";

    interface Columns {
        String PRODUCT_ID = "PRODUCT_ID";
        String QUANTITY = "QUANTITY";
    }
    // product

    @Column(name = Columns.QUANTITY, nullable = false)
    private float quantity;
}
