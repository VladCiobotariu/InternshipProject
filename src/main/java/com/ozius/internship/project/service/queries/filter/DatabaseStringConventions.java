package com.ozius.internship.project.service.queries.filter;

import com.ozius.internship.project.service.queries.filter.DatabaseFormat;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class DatabaseStringConventions {

    private final Map<String, DatabaseFormat> criteriaToFormatInDataBase= new HashMap<>();

    public DatabaseStringConventions() {
        mapCriteriaToFormatInDataBase("productName", DatabaseFormat.FIRST_LETTER_UPPER_CASE);
        mapCriteriaToFormatInDataBase("categoryName", DatabaseFormat.FIRST_LETTER_UPPER_CASE);
        mapCriteriaToFormatInDataBase("cityName", DatabaseFormat.FIRST_LETTER_UPPER_CASE);
    }

    protected void mapCriteriaToFormatInDataBase(String criteria, DatabaseFormat databaseFormat) {
        criteriaToFormatInDataBase.put(criteria, databaseFormat);
    }

    public Map<String, DatabaseFormat> getCriteriaToFormatInDataBase() {
        return Collections.unmodifiableMap(criteriaToFormatInDataBase);
    }
}
