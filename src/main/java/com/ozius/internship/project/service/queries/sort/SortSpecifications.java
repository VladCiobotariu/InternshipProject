package com.ozius.internship.project.service.queries.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortSpecifications {

    private final List<SortCriteria> sortCriteria;

    public SortSpecifications() {
        this.sortCriteria = new ArrayList<>();
    }

    public List<SortCriteria> getSortCriteria() {
        return Collections.unmodifiableList(sortCriteria);
    }

    public void addSortCriteria(SortCriteria criteria) {
        this.sortCriteria.add(criteria);
    }

}
