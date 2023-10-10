package com.ozius.internship.project.dto;

import com.ozius.internship.project.domain.product.UnitOfMeasure;
import lombok.Data;

@Data
public class ProductDTO {
    protected long id;
    protected String description;
    protected String imageName;
    protected String name;
    protected float price;
    protected CategoryDTO category;
    protected SellerDTO seller;
    protected UnitOfMeasure unitOfMeasure;
    protected Double productRating;
    protected long numberReviews;
    protected boolean isRatingApplicable;

}
