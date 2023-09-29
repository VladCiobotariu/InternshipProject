package com.ozius.internship.project.dto;

import lombok.Data;

@Data
public class ReviewsInformationDTO {

    private Float productRating;
    private Integer numberReviews;
    private Boolean areReviewsDisplayed;

    public ReviewsInformationDTO(Float productRating, Integer numberReviews) {
        this.productRating = productRating;
        this.numberReviews = numberReviews;
        if(numberReviews > 2) {
            this.areReviewsDisplayed = true;
        } else {
            this.areReviewsDisplayed = false;
        }
    }
}
