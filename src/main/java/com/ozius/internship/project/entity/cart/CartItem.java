package com.ozius.internship.project.entity.cart;

import com.ozius.internship.project.entity.BaseEntity;
import com.ozius.internship.project.entity.product.Product;
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

    @Column(name = Columns.QUANTITY, nullable = false)
    private float quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Columns.PRODUCT_ID, nullable = false, foreignKey = @ForeignKey(foreignKeyDefinition =
            "FOREIGN KEY (" + Columns.PRODUCT_ID + ") REFERENCES " + Product.TABLE_NAME + " (" + BaseEntity.ID + ")  ON DELETE CASCADE"))
    private Product product;

    protected CartItem() {
    }

    CartItem(float quantity, Product product) {
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

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + getId() +
                "quantity=" + quantity +
                ", product=" + product.getName() +
                '}';
    }
}
