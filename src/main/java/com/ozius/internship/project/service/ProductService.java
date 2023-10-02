package com.ozius.internship.project.service;

import com.ozius.internship.project.dto.ProductWithRatingsDTO;
import com.ozius.internship.project.dto.ReviewDTO;
import com.ozius.internship.project.dto.ReviewsInformationDTO;
import com.ozius.internship.project.entity.product.Product;
import com.ozius.internship.project.entity.seller.Review;
import com.ozius.internship.project.repository.ProductRepository;
import jakarta.persistence.Tuple;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ProductService(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    public ProductWithRatingsDTO getProductWithReviews(long id) {
        Product product = productRepository.findById(id).orElseThrow();
        ReviewsInformationDTO reviewInfo = productRepository.calculateReviewInfoForProduct(id);
        reviewInfo.setRatingApplicable(reviewInfo.getNumberReviews() > 2);

        ProductWithRatingsDTO productWithRatingsDTO = modelMapper.map(product, ProductWithRatingsDTO.class);
        productWithRatingsDTO.setReviewInformation(reviewInfo);

        return productWithRatingsDTO;
    }

    public List<ReviewDTO> getReviewsForProduct(long id) {
        List<Review> reviews = productRepository.getReviewsForProduct(id);

        return reviews.stream()
                .map(review -> modelMapper.map(review, ReviewDTO.class))
                .collect(Collectors.toList());
    }
}
