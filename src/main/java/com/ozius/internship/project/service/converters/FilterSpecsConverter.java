package com.ozius.internship.project.service.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.ozius.internship.project.service.queries.filter.DatabaseFormat;
import com.ozius.internship.project.service.queries.filter.FilterCriteria;
import com.ozius.internship.project.service.queries.filter.FilterSpecs;
import com.ozius.internship.project.service.queries.filter.Operation;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;
import static org.apache.commons.lang3.math.NumberUtils.isParsable;

@Component
public class FilterSpecsConverter implements Converter<String, FilterSpecs> {

    private final ObjectMapper objectMapper;
    private final Map<String, DatabaseFormat> criteriaToFormatInDataBase= new HashMap<>();

    public FilterSpecsConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;

        mapCriteriaToFormatInDataBase("productName", DatabaseFormat.FIRST_LETTER_UPPER_CASE);
        mapCriteriaToFormatInDataBase("categoryName", DatabaseFormat.FIRST_LETTER_UPPER_CASE);
        mapCriteriaToFormatInDataBase("cityName", DatabaseFormat.FIRST_LETTER_UPPER_CASE);
    }

    @Override
    public FilterSpecs convert(String source) {
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

        List<String> parts = new ArrayList<>(List.of(filterValue.split(Pattern.quote("[") + "|" + Pattern.quote("]"))));
        if (parts.size() != 3) {
            throw new IllegalArgumentException("Invalid format of filter value " + filterValue);
        }

        String criteria = parts.get(0);
        String operationString = parts.get(1);
        String valueString = parts.get(2);

        Operation operation = Operation.valueOf(operationString.toUpperCase());

        if (isEmpty(operation)) {
            throw new IllegalArgumentException("This operation does not exist: " + operationString);
        }

        List<String> valueParts = new ArrayList<>(List.of(valueString.split("\\|")));

        valueParts
                .forEach(item -> {
                    if (isParsable(item)) {
                        Object value;
                        if (item.contains(".")) {
                            value = Double.parseDouble(valueString);
                        } else {
                            value = Integer.parseInt(item);
                        }
                        listFilterCriteria.add(new FilterCriteria(criteria, operation, value));
                    } else {
                        listFilterCriteria.add(
                                new FilterCriteria(
                                        criteria,
                                        operation,
                                        criteriaToFormatInDataBase.get(criteria).applyFormat(item)
                                ));
                    }
                });

        return listFilterCriteria;
    }

    protected void mapCriteriaToFormatInDataBase(String criteria, DatabaseFormat databaseFormat) {
        criteriaToFormatInDataBase.put(criteria, databaseFormat);
    }

}
