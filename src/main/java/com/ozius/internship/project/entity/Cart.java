package com.ozius.internship.project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = Cart.TABLE_NAME)
public class Cart extends BaseEntity {
    public static final String TABLE_NAME = "cart";

    interface Columns {
        String BUYER_ID = "BUYER_ID";
    }

    // list<cartItems>: cartItems
    // buyer: buyer

    public float calculateTotalPrice() { return 0; }

    public float calculateItemPrice() { return 0; }

    public void modifyItem(CartItem cartItem, float quantity) {}

    public void addToCart(Product product, float quantity) {}

    public void removeFromCart(Product product) {}
}
