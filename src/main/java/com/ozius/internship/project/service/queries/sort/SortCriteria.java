package com.ozius.internship.project.service.queries.sort;

import java.util.Objects;

public class SortCriteria {

    private final String criteria; // name or price
    private final SortOrder sortOrder; // asc or desc

    public SortCriteria(String criteria, SortOrder sortOrder) {
        this.criteria = criteria;
        this.sortOrder = sortOrder;
    }

    public String getCriteria() {
        return criteria;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SortCriteria that = (SortCriteria) o;
        return Objects.equals(criteria, that.criteria) && sortOrder == that.sortOrder;
    }

    @Override
    public int hashCode() {
        return Objects.hash(criteria, sortOrder);
    }
}
