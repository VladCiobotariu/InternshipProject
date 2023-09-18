package com.ozius.internship.project.service.queries.filter;

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
        mapCriteriaToFormatInDataBase("productName", new FilterConfiguration<>(Operation.LIKE, new CapitalizeConverter()));
        mapCriteriaToFormatInDataBase("categoryName", new FilterConfiguration<>(Operation.EQ, new NoTransformationConverter()));
        mapCriteriaToFormatInDataBase("cityName", new FilterConfiguration<>(Operation.EQ, value -> value));
        mapCriteriaToFormatInDataBase("orderStatus", new FilterConfiguration<>(Operation.EQ, new OrderStatusConverter()));
    }

    public FilterConfiguration<?> getConfigurationForFilter(String filter){
        return filterConfigurationMap.get(filter);
    }

    private void mapCriteriaToFormatInDataBase(String criteria, FilterConfiguration<?> filterConfiguration) {
        filterConfigurationMap.put(criteria, filterConfiguration);
    }
}