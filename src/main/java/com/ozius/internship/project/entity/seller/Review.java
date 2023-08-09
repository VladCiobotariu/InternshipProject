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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Columns.BUYER_ID, foreignKey = @ForeignKey(foreignKeyDefinition = "FOREIGN KEY (" + Columns.BUYER_ID + ") REFERENCES " + Review.TABLE_NAME + " (" + BaseEntity.ID + ")  ON DELETE SET NULL"))
    private Buyer buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Columns.PRODUCT_ID, foreignKey = @ForeignKey(foreignKeyDefinition = "FOREIGN KEY (" + Columns.PRODUCT_ID + ") REFERENCES " + Review.TABLE_NAME + " (" + BaseEntity.ID + ")  ON DELETE SET NULL"))
    private Product product;

    protected Review() {
    }

    Review(String description, float rating, Buyer buyer, Product product) {
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

    //TODO I think we dont want to update a buyer or a product for a review, only description, or maybe the rating but this is debate-able
    public void updateReview(String description, float rating, Buyer buyer, Product product) {
        this.description = description;
        this.rating = rating;
        this.buyer = buyer;
        this.product = product;
    }

    @Override
    public String toString() {
        return "Review{" +
                "description='" + description + '\'' +
                ", rating=" + rating +
                ", buyerInfo=" + buyer.getAccount().getFirstName() +
                ", seller=" + product.getSeller().getAlias() +
                ", product=" + product.getName() +
                '}';
    }
}
