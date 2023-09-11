package com.ozius.internship.project.service.queries;


import com.ozius.internship.project.dto.ProductDTO;
import com.ozius.internship.project.entity.Product;
import jakarta.persistence.EntityManager;
import org.modelmapper.ModelMapper;

public class ProductSearchQuery extends JpaQueryBuilder<Product, ProductDTO> {

    private final ModelMapper modelMapper;

    public ProductSearchQuery(ModelMapper modelMapper, EntityManager em) {
        super("select p from Product p ",em, Product.class);
        this.modelMapper = modelMapper;
    }

    @Override
    public ResultTransformer<Product, ProductDTO> getTransformer() {
        return new ModelMapperBasedResultTransformer<>(modelMapper, ProductDTO.class);
    }

    public ProductSearchQuery withCategory(String category){
        and("p.category.name = :categoryName", "categoryName", category);
        return this;
    }

    public ProductSearchQuery withCity(String city){
        and("p.seller.legalAddress.city = :cityName", "cityName", city);
        return this;
    }

    public ProductSearchQuery withPriceFrom(Float priceFrom){
        and("p.price >= :priceFrom", "priceFrom", priceFrom);
        return this;
    }

    public ProductSearchQuery withPriceTo(Float priceTo){
        and("p.price <= :priceTo", "priceTo", priceTo);
        return this;
    }
}
