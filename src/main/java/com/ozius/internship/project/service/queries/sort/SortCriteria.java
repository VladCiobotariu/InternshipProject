package com.ozius.internship.project.service.queries.sort;

import java.util.Objects;

public class SortCriteria {

    private final String name;
    private final SortOrder sortOrder; // asc or desc

    public SortCriteria(String name, SortOrder sortOrder) {
        this.name = name;
        this.sortOrder = sortOrder;
    }

    public String getName() {
        return name;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SortCriteria that = (SortCriteria) o;
        return Objects.equals(name, that.name) && sortOrder == that.sortOrder;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, sortOrder);
    }
}
