package com.ozius.internship.project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = Review.TABLE_NAME)
public class Review extends BaseEntity {
    public static final String TABLE_NAME = "review";

    interface Columns {
        String DESCRIPTION = "DESCRIPTION";
        String RATING = "RATING";
        String SELLER_ID = "SELLER_ID";
        String BUYER_ID = "BUYER_ID";
    }

    // seller
    // buyer

    @Column(name = Columns.DESCRIPTION, length = 250, nullable = false)
    private String description;

    @Column(name = Columns.RATING, nullable = false)
    private float rating;
}
