package com.ozius.internship.project.service.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.ozius.internship.project.service.queries.filter.*;
import com.ozius.internship.project.service.queries.filter.converter.FilterConfiguration;
import com.ozius.internship.project.service.queries.filter.converter.FilterValueConverter;
import jakarta.annotation.Nullable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;

@Component
public class FilterSpecsConverter implements Converter<String, FilterSpecs> {

    private final ObjectMapper objectMapper;
    private final FilterConfigurationProvider filterConfigurationProvider;

    public FilterSpecsConverter(ObjectMapper objectMapper, FilterConfigurationProvider filterConfigurationProvider) {
        this.objectMapper = objectMapper;
        this.filterConfigurationProvider = filterConfigurationProvider;
    }

    @Override
    public FilterSpecs convert(@Nullable String source) {
        TypeFactory typeFactory = objectMapper.getTypeFactory();

        try {
            List<String> filterValues = objectMapper.readValue(source, typeFactory.constructCollectionType(List.class, String.class));

            return buildFilterSpec(filterValues);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public FilterSpecs buildFilterSpec(List<String> filterValues) {
        FilterSpecs filterSpecs = new FilterSpecs();

        filterValues.stream()
                .map(this::parseFilterCriteria)
                .flatMap(List::stream)
                .forEach(filterSpecs::addFilterCriteria);

        return filterSpecs;
    }

    public List<FilterCriteria> parseFilterCriteria(String filterValue) {
        List<FilterCriteria> listFilterCriteria = new ArrayList<>();
        List<String> parts = List.of(filterValue.split(Pattern.quote("[") + "|" + Pattern.quote("]")));

        if (parts.size() != 3) {
            throw new IllegalArgumentException("Invalid format of filter value " + filterValue);
        }

        String criteria = parts.get(0);
        String operationString = parts.get(1);
        String valueString = parts.get(2);

        Operation operation;

        if (isEmpty(operationString)) {
            FilterConfiguration<?> configurationForFilter = filterConfigurationProvider.getConfigurationForFilter(criteria);
            operation = configurationForFilter.getDefaultOperation();
        } else {
            operation = Operation.valueOf(operationString.toUpperCase());
        }

        List<String> valueParts = new ArrayList<>(List.of(valueString.split("\\|")));
        FilterValueConverter<?> converter = filterConfigurationProvider.getConfigurationForFilter(criteria).getFilterValueConverter();

        valueParts
                .forEach(item -> {
                    Object value;
                    value = converter.convert(item);
                    listFilterCriteria.add(new FilterCriteria(criteria, operation, value));
                });

        return listFilterCriteria;
    }
}
