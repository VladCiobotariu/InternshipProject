package com.ozius.internship.project.controller;

import com.ozius.internship.project.dto.ProductDTO;
import com.ozius.internship.project.repository.ProductRepository;
import com.ozius.internship.project.service.ProductService;
import com.ozius.internship.project.service.queries.ProductPaginationSearchQuery;
import jakarta.persistence.EntityManager;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@RestController
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final EntityManager entityManager;

    public ProductController(ProductService productService, ProductRepository productRepository, ModelMapper modelMapper, EntityManager entityManager) {
        this.productService = productService;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.entityManager = entityManager;
    }

    @GetMapping("/products")
    public ApiPaginationResponse<List<ProductDTO>> getProductsByFilter(@RequestParam(name = "itemsPerPage", defaultValue = "10") int itemsPerPage,
                                                                       @RequestParam(name = "page", defaultValue = "1") int page,
                                                                       @RequestParam(name = "categoryName", required = false) String categoryName,
                                                                       @RequestParam(name = "city", required = false) String city,
                                                                       @RequestParam(name = "priceFrom", required = false) Float priceFrom,
                                                                       @RequestParam(name = "priceTo", required = false) Float priceTo) {

        String newCatName;
        // set categoryName first letter to uppercase
        if(isNotEmpty(categoryName)) {
            newCatName = categoryName.substring(0, 1).toUpperCase() + categoryName.substring(1);
        } else {
            newCatName = null;
        }
        ProductPaginationSearchQuery query = new ProductPaginationSearchQuery(modelMapper, entityManager)
                .withCategory(newCatName)
                .withCity(city)
                .withPriceFrom(priceFrom)
                .withPriceTo(priceTo);


        List<ProductDTO> productsDTO = query.getPagingResultList(itemsPerPage, page-1);

        return new ApiPaginationResponse<>(page, itemsPerPage, productsDTO.size(), productsDTO);
    }

}
