package com.ozius.internship.project.service;

import com.ozius.internship.project.dto.ProductDTO;
import com.ozius.internship.project.dto.ProductWithRatingsDTO;
import com.ozius.internship.project.dto.ReviewDTO;
import com.ozius.internship.project.dto.ReviewsInformationDTO;
import com.ozius.internship.project.entity.product.Product;
import com.ozius.internship.project.entity.seller.Review;
import com.ozius.internship.project.repository.ProductRepository;
import jakarta.persistence.Tuple;
import org.hibernate.sql.results.internal.TupleImpl;
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

    public ProductDTO getProductById(long id) {
        Product product = productRepository.findById(id).orElseThrow();
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        return productDTO;
    }

    public ProductWithRatingsDTO getProductWithReviews(long id) {
        Product product = productRepository.findById(id).orElseThrow();
        Tuple reviewInfo = productRepository.calculateReviewInfoForProduct(id);

        // todo - ask if this retrieving is ok
        Double productRatingDouble = (Double) reviewInfo.get("productRating");
        Float productRating = productRatingDouble != null ? productRatingDouble.floatValue() : null;
        Long numberReviewsLong = (Long) reviewInfo.get("numberReviews");
        Integer numberReviews = numberReviewsLong != null ? numberReviewsLong.intValue() : null;

        ReviewsInformationDTO reviewsInformation = new ReviewsInformationDTO(productRating, numberReviews);

        // still have to make 2 requests to the backend because i need to transform the product into a dto
        ProductWithRatingsDTO productWithRatingsDTO = modelMapper.map(product, ProductWithRatingsDTO.class);
        productWithRatingsDTO.setReviewInformation(reviewsInformation);

        return productWithRatingsDTO;
    }

    public List<ReviewDTO> getReviewsForProduct(long id) {
        List<Review> reviews = productRepository.getReviewsForProduct(id);

        List<ReviewDTO> reviewDTOS = reviews.stream()
                .map(review -> modelMapper.map(review, ReviewDTO.class))
                .collect(Collectors.toList());

        return reviewDTOS;
    }
}
