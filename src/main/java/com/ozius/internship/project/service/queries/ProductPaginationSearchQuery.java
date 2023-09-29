package com.ozius.internship.project.service.queries;

import com.ozius.internship.project.dto.ProductDTO;
import com.ozius.internship.project.dto.ProductWithRatingsDTO;
import com.ozius.internship.project.entity.product.Product;
import com.ozius.internship.project.service.queries.filter.FilterSpecs;
import com.ozius.internship.project.service.queries.sort.SortSpecs;
import jakarta.persistence.EntityManager;
import org.modelmapper.ModelMapper;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

public class ProductPaginationSearchQuery extends PagingJpaQueryBuilder<Product,ProductWithRatingsDTO> {

    private final ModelMapper modelMapper;

    public ProductPaginationSearchQuery(ModelMapper modelMapper, EntityManager em) {
        super("select p from Product p ", em, Product.class);
        this.modelMapper = modelMapper;

        mapCriteriaToPropertyPath("productPrice", "p.price");
        mapCriteriaToPropertyPath("productName", "p.name");
        mapCriteriaToPropertyPath("categoryName", "p.category.name");
        mapCriteriaToPropertyPath("cityName", "p.seller.legalAddress.city");
        mapCriteriaToPropertyPath("priceFrom", "p.price");
        mapCriteriaToPropertyPath("priceTo", "p.price");
    }

    @Override
    public String orderByDefault() {
        return "p.id";
    }

    @Override
    public ResultTransformer<Product, ProductWithRatingsDTO> getTransformer() {
        return new ModelMapperBasedResultTransformer<>(modelMapper, ProductWithRatingsDTO.class);
    }

    public ProductPaginationSearchQuery orderBy(SortSpecs sortSpecs) {
        if(isNotEmpty(sortSpecs)) {
            applySortSpecs(sortSpecs);
        }
        return this;
    }

    public ProductPaginationSearchQuery filterBy(FilterSpecs filterSpecs) {
        if(isNotEmpty(filterSpecs)) {
            applyFilterSpecs(filterSpecs);
        }
        return this;
    }

}
