package com.ozius.internship.project.entity;

import jakarta.persistence.*;

@Entity
@Table(name = Order.TABLE_NAME)
public class Order {

    public static final String TABLE_NAME = "ORDER";

    interface Columns{
        String BUYER_EMAIL = "BUYER_EMAIL";
        String ADDRESS = "ADDRESS";
        String SELLER_ID = "SELLER_ID";
        String BUYER_ID = "BUYER_ID";
        String TELEPHONE = "TELEPHONE";
        String ORDER_STATUS = "ORDER_STATUS";
        String ORDER_DATE = "ORDER_DATE";
        String TOTAL_PRICE = "TOTAL_PRICE";
    }

    @Enumerated(EnumType.STRING)
    @Column(name = Columns.ORDER_STATUS, length = 15, nullable = false)
    private OrderStatus orderStatus;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "country", column = @Column(name = "COUNTRY")),
            @AttributeOverride( name = "state", column = @Column(name = "STATE")),
            @AttributeOverride( name = "city", column = @Column(name = "CITY")),
            @AttributeOverride( name = "addressLine1", column = @Column(name = "ADDRESS_LINE_1")),
            @AttributeOverride( name = "addressLine2", column = @Column(name = "ADDRESS_LINE_2")),
            @AttributeOverride( name = "zipCode", column = @Column(name = "ZIP_CODE"))
    })
    private Address address;

}
