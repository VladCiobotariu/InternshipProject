package com.ozius.internship.project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = Product.TABLE_NAME)
public class Product extends BaseEntity {
    public static final String TABLE_NAME = "product";

    interface Columns {
        String NAME = "NAME";
        String DESCRIPTION = "DESCRIPTION";
        String IMAGE_NAME = "IMAGE_NAME";
        String PRICE = "PRICE";
        String CATEGORY_ID = "CATEGORY_ID";
        String SELLER_ID = "SELLER_ID";

    }

    // seller
    // reviews: List<Review>
    // category

    @Column(name = Columns.NAME, length = 50, nullable = false)
    private String name;

    @Column(name = Columns.DESCRIPTION, length = 250, nullable = false)
    private String description;

    @Column(name = Columns.IMAGE_NAME, length = 250, nullable = false)
    private String imageName;

    @Column(name = Columns.PRICE, nullable = false)
    private float price;
}
