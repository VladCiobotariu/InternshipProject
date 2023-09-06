package com.ozius.internship.project.controller;

import com.ozius.internship.project.dto.ProductDTO;
import com.ozius.internship.project.entity.Product;
import com.ozius.internship.project.repository.ProductRepository;
import com.ozius.internship.project.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;

    private final ModelMapper modelMapper;

    public ProductController(ProductService productService, ProductRepository productRepository, ModelMapper modelMapper) {
        this.productService = productService;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/products")
    public ApiPaginationResponse<List<ProductDTO>> getProductsByCategoryNamePageable(@RequestParam(name = "itemsPerPage", defaultValue = "10") int itemsPerPage,
                                                                                    @RequestParam(name = "page", defaultValue = "1") int page,
                                                                                    @RequestParam(name="categoryName", defaultValue = "") String categoryName) {

        Pageable pageable = PageRequest.of(page-1, itemsPerPage);
        Page<Product> productPage;

        if(!Objects.equals(categoryName, "")) {
            productPage = productRepository.findByCategoryName(categoryName, pageable);
        }
        else {
            productPage = productRepository.findAll(pageable);
        }

        List<Product> products = productPage.getContent();
        int totalElements = (int) productPage.getTotalElements();
        List<ProductDTO> productsDTO = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
        return new ApiPaginationResponse<>(page, itemsPerPage, totalElements, productsDTO);
    }

}
