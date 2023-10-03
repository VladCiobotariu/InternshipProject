package com.ozius.internship.project.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductWithRatingsDTO extends ProductDTO {

    private float productRatings;
    private Set<ReviewDTO> reviews;

    public void setProductRatings(float productRatings) {
        this.productRatings = productRatings;
    }

    public void setRatingItems(Set<ReviewDTO> reviews) {
        this.reviews = reviews;
    }
}
