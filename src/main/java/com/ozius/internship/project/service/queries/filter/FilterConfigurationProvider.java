package com.ozius.internship.project.service.queries.filter;

import com.ozius.internship.project.entity.order.OrderStatus;
import com.ozius.internship.project.service.queries.filter.converter.CapitalizeConverter;
import com.ozius.internship.project.service.queries.filter.converter.FilterConfiguration;
import com.ozius.internship.project.service.queries.filter.converter.NoTransformationConverter;
import com.ozius.internship.project.service.queries.filter.converter.OrderStatusConverter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class FilterConfigurationProvider {

    private final Map<String, FilterConfiguration<?>> filterConfigurationMap = new HashMap<>();

    public FilterConfigurationProvider() {
        mapCriteriaToFormatInDataBase("productName", new FilterConfiguration<String>(Operation.LIKE, new CapitalizeConverter()));
        mapCriteriaToFormatInDataBase("categoryName", new FilterConfiguration<String>(Operation.EQ, new NoTransformationConverter()));
        mapCriteriaToFormatInDataBase("cityName", new FilterConfiguration<String>(Operation.EQ, new NoTransformationConverter()));
        mapCriteriaToFormatInDataBase("orderStatus", new FilterConfiguration<OrderStatus>(Operation.EQ, new OrderStatusConverter()));
    }

    public FilterConfiguration<?> getConfigurationForFilter(String filter){
        return filterConfigurationMap.get(filter);
    }

    private void mapCriteriaToFormatInDataBase(String criteria, FilterConfiguration<?> filterConfiguration) {
        filterConfigurationMap.put(criteria, filterConfiguration);
    }
}

/**
 * http://localhost:8080/products?filter=["categoryName[eq]fruits|vegetables","cityName[eq]timisoara","priceFrom[gt]10|6"]
 * http://localhost:8080/products?filter=["categoryName[]fruits|vegetables","cityName[eq]timisoara","priceFrom[gt]10|6"]
 */