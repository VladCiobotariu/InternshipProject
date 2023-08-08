package com.ozius.internship.project.entity.order;

import com.ozius.internship.project.entity.BaseEntity;
import com.ozius.internship.project.entity.Product;
import jakarta.persistence.*;

@Entity
@Table(name = OrderItem.TABLE_NAME)
public class OrderItem extends BaseEntity {

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
    @JoinColumn(name = Columns.PRODUCT_ID, foreignKey = @ForeignKey(foreignKeyDefinition = "FOREIGN KEY (" + Columns.PRODUCT_ID + ") REFERENCES " + Product.TABLE_NAME + " (" + BaseEntity.ID + ")  ON DELETE SET NULL"))
    private Product product;

    @Column(name = Columns.QUANTITY, nullable = false)
    private float quantity;

    //TODO please use a better self-explanatory name
    @Column(name = Columns.NAME, nullable = false)
    private String name;

    //TODO please use a better self-explanatory name. Not clear if it's price of the order item or product price.
    @Column(name = Columns.PRICE, nullable = false)
    private float price;

    @Column(name = Columns.DESCRIPTION, nullable = false)
    private String description;

    protected OrderItem() {
    }

    public OrderItem(Product product, float quantity) { //TODO use package constructor. Order item to be managed via Order(Aggregate Root)
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

    public void setProductNull() {
        this.product = null;
    }

    //todo toString() missing usually useful for debug purpose.
}
