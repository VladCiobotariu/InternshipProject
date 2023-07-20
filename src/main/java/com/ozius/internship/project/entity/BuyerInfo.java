package com.ozius.internship.project.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = BuyerInfo.TABLE_NAME)
public class BuyerInfo extends BaseEntity{

    public static final String TABLE_NAME = "BUYER_INFO";
    public static final String JOIN_TABLE_NAME = "BUYER_FAVORITES";

    interface Columns{
        String ACCOUNT_ID = "ACCOUNT_ID";
        String CART_ID = "CART_ID";
        String BUYER_ID = "BUYER_ID";
        String PRODUCT_ID = "PRODUCT_ID";

    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = Columns.CART_ID, nullable = false)
    private Cart cart;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = Columns.ACCOUNT_ID, nullable = false)
    private UserAccount account;

    @ManyToMany/*Cascade all for join table*/
    @JoinTable(
            name = JOIN_TABLE_NAME,
            joinColumns = @JoinColumn(name = Columns.BUYER_ID),
            inverseJoinColumns = @JoinColumn(name = Columns.PRODUCT_ID))
    private Set<Product> favoriteProducts;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = BuyerAddress.Columns.BUYER_ID, nullable = false)
    private Set<BuyerAddress> addresses;



}
