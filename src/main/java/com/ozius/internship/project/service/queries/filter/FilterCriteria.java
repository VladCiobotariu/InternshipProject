package com.ozius.internship.project.service.queries.filter;

import java.util.Objects;

public class FilterCriteria {

    private final String criteria;
    private final Operation operation;
    private final Object value;

    public FilterCriteria(String criteria, Operation operation, Object value) {
        this.criteria = criteria;
        this.operation = operation;
        this.value = value;
    }

    public String getCriteria() {
        return criteria;
    }

    public Operation getOperation() {
        return operation;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilterCriteria that = (FilterCriteria) o;
        return Objects.equals(criteria, that.criteria) && operation == that.operation && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(criteria, operation, value);
    }
}
