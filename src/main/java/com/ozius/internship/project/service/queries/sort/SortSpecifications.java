package com.ozius.internship.project.service.queries.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortSpecifications {

    private final List<SortCriteria> sortCriterias;

    public SortSpecifications() {
        this.sortCriterias = new ArrayList<>();
    }

    public List<SortCriteria> getSortCriterias() {
        return Collections.unmodifiableList(sortCriterias);
    }

    public void addSortCriteria(SortCriteria criteria) {
        this.sortCriterias.add(criteria);
    }

}
