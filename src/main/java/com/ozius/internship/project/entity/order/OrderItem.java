package com.ozius.internship.project.entity.order;

import com.ozius.internship.project.entity.BaseEntity;
import com.ozius.internship.project.entity.product.Product;
import jakarta.persistence.*;

@Entity
@Table(name = OrderItem.TABLE_NAME)
public class OrderItem extends BaseEntity {

    public static final String TABLE_NAME = "ORDER_ITEM";

    interface Columns{
        String PRODUCT_ID = "PRODUCT_ID";
        String QUANTITY = "QUANTITY";
        String ITEM_NAME = "ITEM_NAME";
        String ITEM_PRICE = "ITEM_PRICE";
        String DESCRIPTION = "DESCRIPTION";
        String ORDER_ID = "ORDER_ID";
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Columns.PRODUCT_ID, foreignKey = @ForeignKey(foreignKeyDefinition = "FOREIGN KEY (" + Columns.PRODUCT_ID + ") REFERENCES " + Product.TABLE_NAME + " (" + BaseEntity.ID + ")  ON DELETE SET NULL"))
    private Product product;

    @Column(name = Columns.QUANTITY, nullable = false)
    private float quantity;

    @Column(name = Columns.ITEM_NAME, nullable = false)
    private String productName;

    @Column(name = Columns.ITEM_PRICE, nullable = false)
    private float itemPrice;

    @Column(name = Columns.DESCRIPTION, nullable = false)
    private String description;

    protected OrderItem() {
    }

    OrderItem(Product product, float quantity) {
        this.product = product;
        this.quantity = quantity;
        this.productName = product.getName();
        this.itemPrice = product.getPrice();
        this.description = product.getDescription();
    }

    public Product getProduct() {
        return product;
    }

    public float getQuantity() {
        return quantity;
    }

    public String getProductName() {
        return productName;
    }

    public float getItemPrice() {
        return itemPrice;
    }

    public String getDescription() {
        return description;
    }


    @Override
    public String toString() {
        return "OrderItem{" +
                "product=" + product.getName() +
                ", quantity=" + quantity +
                ", name='" + productName + '\'' +
                ", price=" + itemPrice +
                ", description='" + description + '\'' +
                '}';
    }
}
