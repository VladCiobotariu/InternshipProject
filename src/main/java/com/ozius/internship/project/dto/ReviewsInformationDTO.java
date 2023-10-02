package com.ozius.internship.project.dto;

import lombok.Data;

@Data
public class ReviewsInformationDTO {

    private Double productRating;
    private long numberReviews;
    private boolean isRatingApplicable;

    public ReviewsInformationDTO(Double productRating, long numberReviews) {
        this.productRating = productRating;
        this.numberReviews = numberReviews;
    }
}
