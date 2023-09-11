package com.ozius.internship.project.service.queries;

import jakarta.persistence.EntityManager;

public class ProductSearchQueryArrayBased extends JpaQueryBuilder<Object[], ProductBaseDto> {

    public ProductSearchQueryArrayBased(EntityManager em) {
        super("select p.name, p.description from product p ",em, Object[].class);
    }

    @Override
    public ResultTransformer<Object[], ProductBaseDto> getTransformer() {
        return input -> new ProductBaseDto((String) input[0], (String) input[1]);
    }

    public ProductSearchQueryArrayBased withCategory(String category){
        and("p.category.name = :categoryName", "categoryName", category);
        return this;
    }

    public ProductSearchQueryArrayBased withCity(String city){
        and("p.seller.legalAddress.city = :cityName", "cityName", city);
        return this;
    }

    public ProductSearchQueryArrayBased withPriceFrom(Float priceFrom){
        and("p.price >= :priceFrom", "priceFrom", priceFrom);
        return this;
    }

    public ProductSearchQueryArrayBased withPriceTo(Float priceTo){
        and("p.price <= :priceTo", "priceTo", priceTo);
        return this;
    }
}
