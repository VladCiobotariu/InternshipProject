package com.ozius.internship.project.controller;

import com.ozius.internship.project.dto.ProductDTO;
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
    public ApiPaginationResponse<List<ProductDTO>> getProductsByFilter(@RequestParam(name = "itemsPerPage", defaultValue = "10") int itemsPerPage,
                                                                       @RequestParam(name = "page", defaultValue = "1") int page,
                                                                       @RequestParam(name = "sort", required = false) SortSpecs sortSpecs,
                                                                       @RequestParam(name = "filter", required = false) FilterSpecs filterSpecs) {


        ProductPaginationSearchQuery query = new ProductPaginationSearchQuery(modelMapper, entityManager)
                .filterBy(filterSpecs)
                .orderBy(sortSpecs);

        List<ProductDTO> productsDTO = query.getPagingResultList(itemsPerPage, page-1);

        return new ApiPaginationResponse<>(page, itemsPerPage, productsDTO.size(), productsDTO);
    }

    @GetMapping("/products/{productName}")
    public ResponseEntity<ProductDTO> getProductByName(@PathVariable String productName) {
        ProductDTO productDTO =  productService.getProductByName(productName);
        return ResponseEntity.ok(productDTO);
    }

}
