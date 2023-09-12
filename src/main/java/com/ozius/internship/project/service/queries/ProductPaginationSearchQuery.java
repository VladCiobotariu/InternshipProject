package com.ozius.internship.project.service.queries;

import com.ozius.internship.project.dto.ProductDTO;
import com.ozius.internship.project.entity.Product;
import com.ozius.internship.project.service.queries.sort.SortSpecifications;
import com.ozius.internship.project.service.queries.sort.SortSpecificationsParser;
import jakarta.persistence.EntityManager;
import org.modelmapper.ModelMapper;

import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

public class ProductPaginationSearchQuery extends PagingJpaQueryBuilder<Product,ProductDTO> {

    private final ModelMapper modelMapper;

    public ProductPaginationSearchQuery(ModelMapper modelMapper, EntityManager em) {
        super("select p from Product p ", em, Product.class);
        this.modelMapper = modelMapper;

        mapCriteriaToPropertyPath("productPrice", "p.price");
        mapCriteriaToPropertyPath("productName", "p.name");
//        mapCriteriaToPropertyPath("categoryName", "p.category.name");
//        mapCriteriaToPropertyPath("cityName", "p.seller.legalAddress.city");
//        mapCriteriaToPropertyPath("priceFrom", "p.price");
//        mapCriteriaToPropertyPath("priceTo", "p.price");
    }

    @Override
    public String orderByDefault() {
        return "p.id";
    }

    @Override
    public ResultTransformer<Product, ProductDTO> getTransformer() {
        return new ModelMapperBasedResultTransformer<>(modelMapper, ProductDTO.class);
    }

    public ProductPaginationSearchQuery orderBy(String sort) {
        if(isNotEmpty(sort)) {
            SortSpecifications sortSpecifications = SortSpecificationsParser.parse(sort);
            applySortSpecs(sortSpecifications);
        }
        return this;
    }

}
