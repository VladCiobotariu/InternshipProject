package com.ozius.internship.project.service;

import com.ozius.internship.project.dto.ProductDTO;
import com.ozius.internship.project.dto.ProductWithRatingsDTO;
import com.ozius.internship.project.dto.ReviewDTO;
import com.ozius.internship.project.entity.product.Product;
import com.ozius.internship.project.entity.seller.Review;
import com.ozius.internship.project.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;
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
        Float ratings = productRepository.calculateAverageRatingForProduct(product);
        Set<Review> reviews = productRepository.getReviewsForProduct(product);

        Set<ReviewDTO> reviewDTOS = reviews.stream()
                .map(review -> modelMapper.map(review, ReviewDTO.class))
                .collect(Collectors.toSet());

        ProductWithRatingsDTO productWithRatingsDTO = modelMapper.map(product, ProductWithRatingsDTO.class);
        productWithRatingsDTO.setProductRatings(ratings);
        productWithRatingsDTO.setRatingItems(reviewDTOS);

        return productWithRatingsDTO;
    }

}
