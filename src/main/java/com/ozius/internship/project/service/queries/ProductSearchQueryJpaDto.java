package com.ozius.internship.project.service.queries;

import jakarta.persistence.EntityManager;

public class ProductSearchQueryJpaDto extends JpaDtoBasedQueryBuilder<ProductBaseDto> {


    public ProductSearchQueryJpaDto(EntityManager em) {
        super("select new com.ozius.internship.project.service.queries.ProductBaseDto(p.name, p.description) from Product p ",em , ProductBaseDto.class);
    }

    public ProductSearchQueryJpaDto withCategory(String category){
        and("p.category.name = :categoryName", "categoryName", category);
        return this;
    }

    public ProductSearchQueryJpaDto withCity(String city){
        and("p.seller.legalAddress.city = :cityName", "cityName", city);
        return this;
    }

    public ProductSearchQueryJpaDto withPriceFrom(Float priceFrom){
        and("p.price >= :priceFrom", "priceFrom", priceFrom);
        return this;
    }

    public ProductSearchQueryJpaDto withPriceTo(Float priceTo){
        and("p.price <= :priceTo", "priceTo", priceTo);
        return this;
    }
}
