package com.ozius.internship.project.service.queries;

import com.ozius.internship.project.dto.ProductDTO;
import com.ozius.internship.project.entity.Product;
import com.ozius.internship.project.service.queries.sort.SortOrder;
import jakarta.persistence.EntityManager;
import org.modelmapper.ModelMapper;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

public class ProductPaginationSearchQuery extends PagingJpaQueryBuilder<Product,ProductDTO> {

    private final ModelMapper modelMapper;

    public ProductPaginationSearchQuery(ModelMapper modelMapper, EntityManager em) {
        super("select p from Product p ", em, Product.class);
        this.modelMapper = modelMapper;
    }

    @Override
    public ResultTransformer<Product, ProductDTO> getTransformer() {
        return new ModelMapperBasedResultTransformer<>(modelMapper, ProductDTO.class);
    }

    @Override
    public String getDefaultSort() {
        return "p.id";
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

    public ProductPaginationSearchQuery orderByCondition(String criteria) {
        if(isNotEmpty(criteria)) {
            if(criteria.equals("price-asc")) {
                orderBy("p.price", SortOrder.ASC);
            }
            if(criteria.equals("price-desc")) {
                orderBy("p.price", SortOrder.DESC);
            }
            if(criteria.equals("name-asc")) {
                orderBy("p.name", SortOrder.ASC);
            }
            if(criteria.equals("name-desc")) {
                orderBy("p.name", SortOrder.DESC);
            }
        }
        return this;
    }

//    public QueryBuilder applySortSpecs(SortSpecifications sortSpecifications) {
//        for (SortCriteria sortCriteria : sortSpecifications.getSortCriterias()) {
//            if(sortCriteria.getSortOrder())
//        }
//    }


}
