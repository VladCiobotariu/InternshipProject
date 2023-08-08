package com.ozius.internship.project.entity;

import jakarta.persistence.*;

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

    protected Category() {
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

    public void updateCategory(Category category) {
        this.name = category.getName();
        this.imageName = category.getImageName();
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", imageName='" + imageName + '\'' +
                '}';
    }
}
