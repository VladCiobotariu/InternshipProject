package com.ozius.internship.project.service.queries.filter.converter;

public interface FilterValueConverter<O> {
    O convert(String value);
}
