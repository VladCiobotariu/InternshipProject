package com.ozius.internship.project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = Category.TABLE_NAME)
public class Category extends BaseEntity {
    public static final String TABLE_NAME = "category";

    interface Columns {
        String NAME = "NAME";
        String IMAGE_NAME = "IMAGE_NAME";
    }

    // products: List<Product>

    @Column(name = Columns.NAME, length = 100, nullable = false, unique = true)
    private String name;

    @Column(name = Columns.IMAGE_NAME, length = 250, nullable = false)
    private String imageName;
}
