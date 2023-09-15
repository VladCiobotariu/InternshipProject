package com.ozius.internship.project.service.queries.filter.converter;

import com.ozius.internship.project.service.queries.filter.Operation;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FilterConfiguration<T> {
    private Operation defaultOperation;
    private FilterValueConverter<T> filterValueConverter;
}
