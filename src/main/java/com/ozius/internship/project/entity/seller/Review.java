package com.ozius.internship.project.entity.seller;

import com.ozius.internship.project.entity.BaseEntity;
import com.ozius.internship.project.entity.Buyer;
import com.ozius.internship.project.entity.Product;
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

    //TODO Fix FK constraint violation due to buyer removal. Buyer info should be preserved.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Columns.BUYER_ID)
    private Buyer buyer;

    //TODO Fix FK constraint violation due to buyer removal. Product info should be preserved.
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

    public Buyer getBuyer() {
        return buyer;
    }

    public Product getProduct() {
        return product;
    }

    //TODO setter not allowed, violates encapsulation.
    public void setDescription(String description) {
        this.description = description;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    //TODO - probably single update method with 2 parameters is better. Can be refined later when integrated.
    public void updateDescription(String description) {
        this.setDescription(description);
    }

    public void updateRating(float rating) {
        this.setRating(rating);
    }

    @Override
    public String toString() {
        return "Review{" +
                "description='" + description + '\'' +
                ", rating=" + rating +
                ", buyerInfo=" + buyer +
                ", seller=" + product.getSeller() + //TODO only print seller id(or name/key if defined). Printing the entity can result very long dependency tree being printed.
                ", product=" + product +
                '}';
    }
}
