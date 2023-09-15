package com.ozius.internship.project.service.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.ozius.internship.project.service.queries.filter.FilterCriteria;
import com.ozius.internship.project.service.queries.filter.FilterSpecs;
import com.ozius.internship.project.service.queries.filter.Operation;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;

@Component
public class FilterSpecsConverter implements Converter<String, FilterSpecs> {

    private final ObjectMapper objectMapper;

    public FilterSpecsConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
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

    public FilterSpecs buildFilterSpec (List<String> filterValues) {
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
        if(parts.size() != 3) {
            throw new IllegalArgumentException("Invalid format of filter value " + filterValue);
        }

        String criteria = parts.get(0);
        String operationString = parts.get(1);
        String valueString = parts.get(2);

        Operation operation = Operation.valueOf(operationString.toUpperCase());

        if (isEmpty(operation)) {
            throw new IllegalArgumentException("This operation does not exist: " + operationString);
        }

        try {
            Object value;
            if (valueString.contains(".")) {
                value = Double.parseDouble(valueString);
            } else {
                value = Integer.parseInt(valueString);
            }
            listFilterCriteria.add(new FilterCriteria(criteria, operation, value));

        } catch (NumberFormatException e) {
            if(valueString.contains("|")) {
                List<String> valueParts = new ArrayList<>(List.of(valueString.split("\\|")));
                for (String valuePart : valueParts) {
                    String valueCapitalized = capitalizeFirstLetter(valuePart);
                    String valueHandleLikeCondition = changeValueIfConditionContainsLike(valueCapitalized, operationString);
                    listFilterCriteria.add(new FilterCriteria(criteria, operation, valueHandleLikeCondition));
                }
            }
            else {
                String valueCapitalized = capitalizeFirstLetter(valueString);
                String valueHandleLikeCondition = changeValueIfConditionContainsLike(valueCapitalized, operationString);
                listFilterCriteria.add(new FilterCriteria(criteria, operation, valueHandleLikeCondition));
            }
        }
        return listFilterCriteria;
    }

    private String capitalizeFirstLetter(String value) {
        return value.substring(0, 1).toUpperCase() + value.substring(1);
    }

    private String changeValueIfConditionContainsLike(String value, String operationString) {
        if(operationString.equals("like")) {
            return String.format("%s%%", value);
        }
        return value;
    }
}
