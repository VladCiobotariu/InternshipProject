package com.ozius.internship.project.controller;

import com.ozius.internship.project.dto.ProductWithRatingsDTO;
import com.ozius.internship.project.service.ProductService;
import com.ozius.internship.project.service.queries.ProductPaginationSearchQuery;
import com.ozius.internship.project.service.queries.filter.FilterSpecs;
import com.ozius.internship.project.service.queries.sort.SortSpecs;
import jakarta.persistence.EntityManager;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
public class ProductController {

    private final ModelMapper modelMapper;
    private final EntityManager entityManager;

    private final ProductService productService;

    public ProductController(ModelMapper modelMapper, EntityManager entityManager, ProductService productService) {
        this.modelMapper = modelMapper;
        this.entityManager = entityManager;
        this.productService = productService;
    }

    @GetMapping("/products")
    public ApiPaginationResponse<List<ProductWithRatingsDTO>> getProductsByFilter(@RequestParam(name = "itemsPerPage", defaultValue = "10") int itemsPerPage,
                                                                       @RequestParam(name = "page", defaultValue = "1") int page,
                                                                       @RequestParam(name = "sort", required = false) SortSpecs sortSpecs,
                                                                       @RequestParam(name = "filter", required = false) FilterSpecs filterSpecs) {


        ProductPaginationSearchQuery query = new ProductPaginationSearchQuery(modelMapper, entityManager)
                .filterBy(filterSpecs)
                .orderBy(sortSpecs);

        int numOfTotalProds = query.getResultList().size();

        List<ProductWithRatingsDTO> productWithRatingsDTOS = query.getPagingResultList(itemsPerPage, page-1);

        return new ApiPaginationResponse<>(page, itemsPerPage, numOfTotalProds, productWithRatingsDTOS);
    }

    // todo - get list of reviews for specific product (products/id/reviews)
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductWithRatingsDTO> getProductWithReviews(@PathVariable long id) {
        ProductWithRatingsDTO productWithRatingsDTO = productService.getProductWithReviews(id);
        return ResponseEntity.ok(productWithRatingsDTO);
    }

}
