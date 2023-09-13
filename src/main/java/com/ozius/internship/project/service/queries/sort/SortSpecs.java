package com.ozius.internship.project.service.queries.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortSpecs {

    private final List<SortCriteria> sortCriteria;

    public SortSpecs() {
        this.sortCriteria = new ArrayList<>();
    }

    public List<SortCriteria> getSortCriteria() {
        return Collections.unmodifiableList(sortCriteria);
    }

    public void addSortCriteria(SortCriteria criteria) {
        this.sortCriteria.add(criteria);
    }

}
