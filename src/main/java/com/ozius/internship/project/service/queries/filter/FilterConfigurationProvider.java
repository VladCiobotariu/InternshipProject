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

    //TODO ask about multiple criteria for price,
    // i could not make just one criteria because in query builder it is predefined to make 'OR' building of query if found multiple criteria

    public FilterConfigurationProvider() {
        mapCriteriaToFormatInDataBase("productName", new FilterConfiguration<>(Operation.STARTS_WITH, new CapitalizeConverter()));
        mapCriteriaToFormatInDataBase("categoryName", new FilterConfiguration<>(Operation.EQ, new NoTransformationConverter()));
        mapCriteriaToFormatInDataBase("cityName", new FilterConfiguration<>(Operation.EQ, value -> value));
        mapCriteriaToFormatInDataBase("orderStatus", new FilterConfiguration<>(Operation.EQ, new OrderStatusConverter()));
        mapCriteriaToFormatInDataBase("priceFrom", new FilterConfiguration<>(Operation.GTE, new NoTransformationConverter()));
        mapCriteriaToFormatInDataBase("priceTo", new FilterConfiguration<>(Operation.LTE, new NoTransformationConverter()));
    }

    public FilterConfiguration<?> getConfigurationForFilter(String filter){
        return filterConfigurationMap.get(filter);
    }

    private void mapCriteriaToFormatInDataBase(String criteria, FilterConfiguration<?> filterConfiguration) {
        filterConfigurationMap.put(criteria, filterConfiguration);
    }
}