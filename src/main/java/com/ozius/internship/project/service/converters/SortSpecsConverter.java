package com.ozius.internship.project.service.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.ozius.internship.project.service.queries.sort.SortCriteria;
import com.ozius.internship.project.service.queries.sort.SortOrder;
import org.springframework.core.convert.converter.Converter;
import com.ozius.internship.project.service.queries.sort.SortSpecs;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SortSpecsConverter implements Converter<String, SortSpecs> {

    private final static String SORT_ORDER_SEPARATOR = "-";

    private final ObjectMapper objectMapper;


    public SortSpecsConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public SortSpecs convert(String source) {
        TypeFactory typeFactory = objectMapper.getTypeFactory();

        try {
            List<String> sortValues = objectMapper.readValue(source, typeFactory.constructCollectionType(List.class, String.class));

            return buildSortSpec(sortValues);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public SortSpecs buildSortSpec(List<String> sortValues) {
        SortSpecs sortSpecs = new SortSpecs();

        sortValues.stream()
                .map(this::parseSortCriteria)
                .forEach(sortSpecs::addSortCriteria);

        return sortSpecs;
    }

    public SortCriteria parseSortCriteria(String sortValue) {
        List<String> parts = new ArrayList<>(List.of(sortValue.split(SORT_ORDER_SEPARATOR)));

        if(parts.size() != 2) {
            throw new IllegalArgumentException("Invalid format of sort value " + sortValue);
        }

        if(!parts.get(1).equals("asc") && !parts.get(1).equals("desc")) {
            throw new IllegalArgumentException("Invalid format of sort order " + parts.get(1));
        }

        String criteria = parts.get(0);
        SortOrder sortOrder = SortOrder.valueOf(parts.get(1).toUpperCase());

        return new SortCriteria(criteria, sortOrder);
    }

}
