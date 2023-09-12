package com.ozius.internship.project.service.queries.sort;

import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class SortSpecificationsParser {

    /***
     *
     * @param sortText - sort text to be parsed, in format: price-asc|name-asc|price-desc
     */

    public static SortSpecifications parse(String sortText) {

        SortSpecifications sortSpecifications = new SortSpecifications();
        List<String> criteriaList = new ArrayList<>(List.of(sortText.split("\\|")));

        for(String criterion : criteriaList) {
            List<String> parts = new ArrayList<>(List.of(criterion.split("-")));
            if(parts.size() != 2) {
                throw new IllegalArgumentException("Invalid input given: " + sortText);
            }
            String criteria = parts.get(0);
            SortOrder sortOrder = SortOrder.valueOf(parts.get(1).toUpperCase());

            SortCriteria sortCriteria = new SortCriteria(criteria, sortOrder);
            sortSpecifications.addSortCriteria(sortCriteria);
        }
        return sortSpecifications;

    }


}
