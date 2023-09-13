package com.ozius.internship.project.controller;

import com.ozius.internship.project.dto.ProductDTO;
import com.ozius.internship.project.repository.ProductRepository;
import com.ozius.internship.project.service.ProductService;
import com.ozius.internship.project.service.queries.ProductPaginationSearchQuery;
import com.ozius.internship.project.service.queries.sort.SortSpecifications;
import com.ozius.internship.project.service.queries.sort.SortSpecificationsParser;
import jakarta.persistence.EntityManager;
import org.modelmapper.ModelMapper;
import org.springframework.util.MultiValueMap;
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
                                                                       @RequestParam(name = "sort", required = false) String sort,
                                                                       @RequestParam MultiValueMap<String, String> filter) {


        ProductPaginationSearchQuery query = new ProductPaginationSearchQuery(modelMapper, entityManager)
                .orderBy(sort);

        List<ProductDTO> productsDTO = query.getPagingResultList(itemsPerPage, page-1);

        return new ApiPaginationResponse<>(page, itemsPerPage, productsDTO.size(), productsDTO);
    }

}
