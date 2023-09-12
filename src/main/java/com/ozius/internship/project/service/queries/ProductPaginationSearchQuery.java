package com.ozius.internship.project.service.queries;

import com.ozius.internship.project.dto.ProductDTO;
import com.ozius.internship.project.entity.Product;
import jakarta.persistence.EntityManager;
import org.modelmapper.ModelMapper;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class ProductPaginationSearchQuery extends PagingJpaQueryBuilder<Product,ProductDTO> {

    private final ModelMapper modelMapper;

    public ProductPaginationSearchQuery(ModelMapper modelMapper, EntityManager em) {
        super("select p from Product p ", em, Product.class);
        this.modelMapper = modelMapper;
    }

    @Override
    public String orderByDefault() {
        return "p.id";
    }

    @Override
    public ResultTransformer<Product, ProductDTO> getTransformer() {
        return new ModelMapperBasedResultTransformer<>(modelMapper, ProductDTO.class);
    }

    public ProductPaginationSearchQuery withCategory(String category){
        and("p.category.name = :categoryName", "categoryName", category);
        return this;
    }

    public ProductPaginationSearchQuery withCity(String city){
        and("p.seller.legalAddress.city = :cityName", "cityName", city);
        return this;
    }

    public ProductPaginationSearchQuery withPriceFrom(Float priceFrom){
        and("p.price >= :priceFrom", "priceFrom", priceFrom);
        return this;
    }

    public ProductPaginationSearchQuery withPriceTo(Float priceTo){
        and("p.price <= :priceTo", "priceTo", priceTo);
        return this;
    }

    public ProductPaginationSearchQuery orderByCondition(String condition){
        if(isEmpty(condition)){
            return this;
        }
        if(condition.equals("price-asc")){
            orderBy("p.price", OrderCriteria.ASC);
        }
        if(condition.equals("price-desc")){
            orderBy("p.price", OrderCriteria.DESC);
        }
        if(condition.equals("name-asc")){
            orderBy("p.name", OrderCriteria.ASC);
        }
        return this;
    }

}
