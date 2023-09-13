package com.ozius.internship.project.service.queries;

import com.ozius.internship.project.service.queries.filter.FilterCriteria;
import com.ozius.internship.project.service.queries.filter.FilterSpecs;
import com.ozius.internship.project.service.queries.sort.SortCriteria;
import com.ozius.internship.project.service.queries.sort.SortOrder;
import com.ozius.internship.project.service.queries.sort.SortSpecs;

import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;

public abstract class QueryBuilder {

    protected final StringBuilder queryBuilder;
    protected final Map<String, Object> params;

    private final Map<String, String> criteriaToPropertyPathMappings = new HashMap<>();

    private boolean haveAddedAnyConditions = false;
    private boolean haveAnyOrderConditions = false;

    public QueryBuilder(String query) {
        this.queryBuilder = new StringBuilder(query);
        this.params = new HashMap<>();
    }

    public QueryBuilder and(String condition, String paramName, Object paramValue){
        if(isEmpty(paramValue)){
            return this;
        }
        if(haveAddedAnyConditions){
            queryBuilder.append(String.format(" and %s", condition));
        } else {
            queryBuilder.append(String.format(" where %s", condition));
            haveAddedAnyConditions = true;
        }
        setParameter(paramName, paramValue);

        return this;
    }

    public QueryBuilder orderBy(String orderCondition, SortOrder sortOrder) {
        if(haveAnyOrderConditions) {
            queryBuilder.append(String.format(" , %s %s", orderCondition, sortOrder));
        } else {
            queryBuilder.append(String.format(" order by %s %s", orderCondition, sortOrder));
            haveAnyOrderConditions = true;
        }
        return this;
    }

    protected QueryBuilder mapCriteriaToPropertyPath(String criteria, String propertyPath) {
        criteriaToPropertyPathMappings.put(criteria, propertyPath);
        return this;
    }

    public QueryBuilder applySortSpecs(SortSpecs sortSpecs) {
        for (SortCriteria sortCriterion : sortSpecs.getSortCriteria()) {
            String propertyPath = criteriaToPropertyPathMappings.get(sortCriterion.getCriteria());

            if(propertyPath == null) {
                throw new IllegalArgumentException("Unmapped criteria " + sortCriterion.getCriteria());
            }

            orderBy(propertyPath, sortCriterion.getSortOrder());
        }
        return this;
    }

    public QueryBuilder applyFilterSpecs(FilterSpecs filterSpecs) {
        int paramIndex = 0;
        String condition;
        for (FilterCriteria filterCriterion : filterSpecs.getFilterCriteria()) {
            String propertyPath = criteriaToPropertyPathMappings.get(filterCriterion.getCriteria());
            String sqlOperator = filterCriterion.getOperation().getSqlOperator();

            if(propertyPath == null) {
                throw new IllegalArgumentException("Unmapped criteria " + filterCriterion.getCriteria());
            }
            String paramName = String.format("%s%s", filterCriterion.getCriteria(), paramIndex++);

            if(sqlOperator.equals("like")) {
                condition = String.format("%s %s :%%%s%%", propertyPath, sqlOperator, paramName);
            } else {
                condition = String.format("%s %s :%s", propertyPath, sqlOperator, paramName);
            }

            and(condition, paramName, filterCriterion.getValue());

        }
        return this;
    }


    protected void setParameter(String paramName, Object paramValue){
        params.put(paramName, paramValue);
    }
}
