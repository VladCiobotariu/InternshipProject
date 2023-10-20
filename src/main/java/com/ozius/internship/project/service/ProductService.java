package com.ozius.internship.project.service;

import com.ozius.internship.project.dto.ProductDTO;
import com.ozius.internship.project.dto.ReviewDTO;
import com.ozius.internship.project.entity.product.Product;
import com.ozius.internship.project.entity.seller.Review;
import com.ozius.internship.project.repository.ProductRepository;
import jakarta.transaction.Transactional;
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

    public ProductDTO getProductWithReviews(long id) {
        Product product = productRepository.findById(id).orElseThrow();
        return modelMapper.map(product, ProductDTO.class);
    }

    public List<ReviewDTO> getReviewsForProduct(long id) {
        List<Review> reviews = productRepository.getReviewsForProduct(id);

        return reviews.stream()
                .map(review -> modelMapper.map(review, ReviewDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public void recalculateProductRating(long productId) {
        Product product = productRepository.findById(productId).orElseThrow();
        List<Review> reviews = productRepository.getReviewsForProduct(productId);
        product.updateRatingInformation(reviews);
    }
}
