package com.ozius.internship.project.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private long id;
    private String description;
    private String imageName;
    private String name;
    private float price;
    private CategoryDTO category;
    private SellerDTO seller;

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getImageName() {
        return imageName;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public SellerDTO getSeller() {
        return seller;
    }
}
