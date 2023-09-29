package com.ozius.internship.project.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class ProductWithRatingsDTO extends ProductDTO {

    private float productRatings;
    public void setProductRatings(float productRatings) {
        this.productRatings = productRatings;
    }
}
