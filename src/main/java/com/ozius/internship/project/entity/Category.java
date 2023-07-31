package com.ozius.internship.project.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = Category.TABLE_NAME)
public class Category extends BaseEntity {
    public static final String TABLE_NAME = "category";

    interface Columns {
        String NAME = "NAME";
        String IMAGE_NAME = "IMAGE_NAME";
    }

    @Column(name = Columns.NAME, nullable = false, unique = true)
    private String name;

    @Column(name = Columns.IMAGE_NAME, nullable = false)
    private String imageName;

    public Category() {
    }

    public Category(String name, String imageName) {
        this.name = name;
        this.imageName = imageName;
    }

    public String getName() {
        return name;
    }

    public String getImageName() {
        return imageName;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                '}';
    }
}
