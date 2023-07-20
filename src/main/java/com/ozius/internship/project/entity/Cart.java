package com.ozius.internship.project.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = Cart.TABLE_NAME)
public class Cart extends BaseEntity {
    public static final String TABLE_NAME = "cart";

    interface Columns {
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = CartItem.Columns.PRODUCT_ID)
    private Set<CartItem> cartItems;

    public float calculateTotalPrice() { return 0; }

    public float calculateItemPrice() { return 0; }

    public void modifyItem(CartItem cartItem, float quantity) {}

    public void addToCart(Product product, float quantity) {}

    public void removeFromCart(Product product) {}
}
