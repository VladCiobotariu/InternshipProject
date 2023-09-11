package com.ozius.internship.project.controller;

import com.ozius.internship.project.dto.ProductDTO;
import com.ozius.internship.project.entity.Product;
import com.ozius.internship.project.repository.ProductRepository;
import com.ozius.internship.project.service.ProductService;
import com.ozius.internship.project.service.queries.ProductSearchQuery;
import jakarta.persistence.EntityManager;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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

        // set categoryName first letter to uppercase
        String newCatName = categoryName.substring(0, 1).toUpperCase() + categoryName.substring(1);
        System.out.println(categoryName);
        System.out.println(city);
        ProductSearchQuery query = new ProductSearchQuery(modelMapper, entityManager)
                .withCategory(newCatName);
//                .withCity(city)
//                .withPriceFrom(priceFrom)
//                .withPriceTo(priceTo);

        Pageable pageable = PageRequest.of(page - 1, itemsPerPage);

        List<ProductDTO> productsDTO = query.getResultList();


        return new ApiPaginationResponse<>(page, itemsPerPage, productsDTO.size(), productsDTO);
    }

}
