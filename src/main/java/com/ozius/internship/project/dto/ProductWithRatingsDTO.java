package com.ozius.internship.project.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class ProductWithRatingsDTO extends ProductDTO {

    private ReviewsInformationDTO reviewInformation;
    public void setReviewInformation(ReviewsInformationDTO reviewInformation) {
        this.reviewInformation = reviewInformation;
    }

}
