package com.ozius.internship.project.entity;

import jakarta.persistence.*;

@Entity
@Table(name = Review.TABLE_NAME)
public class Review extends BaseEntity {
    public static final String TABLE_NAME = "review";

    interface Columns {
        String DESCRIPTION = "DESCRIPTION";
        String RATING = "RATING";
        String SELLER_ID = "SELLER_ID";
        String BUYER_ID = "BUYER_ID";
        String PRODUCT_ID = "PRODUCT_ID";

    }
    @Column(name = Columns.DESCRIPTION, length = 250, nullable = false)
    private String description;

    @Column(name = Columns.RATING, nullable = false)
    private float rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Columns.BUYER_ID)
    private Buyer buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Columns.PRODUCT_ID)
    private Product product;

    public Review() {
    }

    public Review(String description, float rating, Buyer buyer, Product product) {
        this.description = description;
        this.rating = rating;
        this.buyer = buyer;
        this.product = product;
    }

    public String getDescription() {
        return description;
    }

    public float getRating() {
        return rating;
    }

    public Buyer getBuyerInfo() {
        return buyer;
    }

    public Product getProduct() {
        return product;
    }

    public void updateDescription(String description){
        this.description = description;
    }

    public void setBuyerInfo(Buyer buyer) {
        this.buyer = buyer;
    }

    @Override
    public String toString() {
        return "Review{" +
                "description='" + description + '\'' +
                ", rating=" + rating +
                ", buyerInfo=" + buyer.getId() +
                ", product=" + product.getId() +
                '}';
    }
}
