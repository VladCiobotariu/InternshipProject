package com.ozius.internship.project.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReviewDTO {

    private long id;
    private String description;
    private float rating;
    private BuyerDTO buyer;
    private LocalDate publishDate;
}
