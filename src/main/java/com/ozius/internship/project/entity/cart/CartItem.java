package com.ozius.internship.project.entity.cart;

import com.ozius.internship.project.entity.BaseEntity;
import com.ozius.internship.project.entity.Product;
import jakarta.persistence.*;

@Entity
@Table(name = CartItem.TABLE_NAME)
public class CartItem extends BaseEntity {
    public static final String TABLE_NAME = "cart_item";

    interface Columns {
        String PRODUCT_ID = "PRODUCT_ID";
        String QUANTITY = "QUANTITY";
        String CART_ID = "CART_ID";
    }
    //TODO - I propose to keep things simple and use int for the quantity.
    @Column(name = Columns.QUANTITY, nullable = false)
    private float quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Columns.PRODUCT_ID, nullable = false)
    private Product product;

    public CartItem() { //TODO use protected for jpa constructors
    }

    public CartItem(float quantity, Product product) { // TODO use package constructor. CartItem to be managed via Cart (Aggregate Root).
        this.quantity = quantity;
        this.product = product;
    }

    public float getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    //todo toString() missing usually useful for debug purpose.
}
