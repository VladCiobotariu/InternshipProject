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

}
