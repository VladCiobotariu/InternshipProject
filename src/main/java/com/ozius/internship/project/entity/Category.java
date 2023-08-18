package com.ozius.internship.project.entity;

import com.ozius.internship.project.entity.exception.IllegalDuplicateName;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

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

    protected static List<String> listOfCategoryNames = new ArrayList<>();

    @Column(name = Columns.IMAGE_NAME, nullable = false)
    private String imageName;

    protected Category() {
    }

    public Category(String name, String imageName) {
        validateForNoDuplicatedNames(name);
        this.name = name;
        this.imageName = imageName;
        listOfCategoryNames.add(name);
    }

    public String getName() {
        return name;
    }

    public String getImageName() {
        return imageName;
    }

    public void updateCategory(String name, String imageName) {
        validateForNoDuplicatedNames(name);
        this.name = name;
        this.imageName = imageName;
    }

    private void validateForNoDuplicatedNames(String name) {
        if(listOfCategoryNames.contains(name)) {
            throw new IllegalDuplicateName("A category with this name already exists!");
        }
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", imageName='" + imageName + '\'' +
                '}';
    }
}
