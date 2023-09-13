package com.ozius.internship.project.service.queries.filter;

import java.util.HashSet;
import java.util.Set;

public class FilterSpecs {

    private final Set<FilterCriteria> filterCriteria;


    public FilterSpecs() {
        this.filterCriteria = new HashSet<>();
    }

    public Set<FilterCriteria> getFilterCriteria() {
        return filterCriteria;
    }

    public void addFilterCriteria(FilterCriteria criteria) {
        this.filterCriteria.add(criteria);
    }
}
