package com.ozius.internship.project.service.queries.filter.converter;

public class CapitalizeConverter implements FilterValueConverter<String>{
    @Override
    public String convert(String value) {
        return value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();
    }
}
