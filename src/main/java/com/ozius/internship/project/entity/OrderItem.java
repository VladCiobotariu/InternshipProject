package com.ozius.internship.project.entity;

import jakarta.persistence.*;

@Entity
@Table(name = OrderItem.TABLE_NAME)
public class OrderItem extends BaseEntity{

    public static final String TABLE_NAME = "ORDER_ITEM";

    interface Columns{
        String PRODUCT_ID = "PRODUCT_ID";
        String QUANTITY = "QUANTITY";
        String NAME = "NAME";
        String PRICE = "PRICE";
        String DESCRIPTION = "DESCRIPTION";
        String ORDER_ID = "ORDER_ID";
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Columns.PRODUCT_ID)
    private Product product;

    @Column(name = Columns.QUANTITY, nullable = false)
    private float quantity;

    @Column(name = Columns.NAME, nullable = false)
    private String name;

    @Column(name = Columns.PRICE, nullable = false)
    private float price;

    @Column(name = Columns.DESCRIPTION, nullable = false)
    private String description;

    public OrderItem() {
    }

    public OrderItem(Product product, float quantity) {
        this.product = product;
        this.quantity = quantity;
        this.name = product.getName();
        this.price = product.getPrice();
        this.description = product.getDescription();
    }

    public Product getProduct() {
        return product;
    }

    public float getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}
