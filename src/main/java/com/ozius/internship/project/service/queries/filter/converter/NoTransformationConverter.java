package com.ozius.internship.project.service.queries.filter.converter;

public class NoTransformationConverter implements FilterValueConverter<String>{
    @Override
    public String convert(String value) {
        return value;
    }
}
