package com.ozius.internship.project.service.queries;

import com.ozius.internship.project.service.queries.filter.FilterCriteria;
import com.ozius.internship.project.service.queries.filter.FilterSpecifications;
import com.ozius.internship.project.service.queries.sort.SortCriteria;
import com.ozius.internship.project.service.queries.sort.SortOrder;
import com.ozius.internship.project.service.queries.sort.SortSpecifications;

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

    public QueryBuilder applySortSpecs(SortSpecifications sortSpecifications) {
        for (SortCriteria sortCriterion : sortSpecifications.getSortCriteria()) {
            String propertyPath = criteriaToPropertyPathMappings.get(sortCriterion.getCriteria());

            if(propertyPath == null) {
                throw new IllegalArgumentException("Unmapped criteria " + sortCriterion.getCriteria());
            }

            orderBy(propertyPath, sortCriterion.getSortOrder());
        }
        return this;
    }

    public QueryBuilder applyFilterSpecs(FilterSpecifications filterSpecifications) {
        int paramIndex = 0;
        for (FilterCriteria filterCriterion : filterSpecifications.getFilterCriteria()) {
            String propertyPath = criteriaToPropertyPathMappings.get(filterCriterion.getCriteria());

            if(propertyPath == null) {
                throw new IllegalArgumentException("Unmapped criteria " + filterCriterion.getCriteria());
            }
            String paramName = String.format("%s%s", filterCriterion.getCriteria(), paramIndex++);

            String condition = String.format("%s %s :%s", propertyPath, filterCriterion.getOperation().getSqlOperator(), paramName);

            and(condition, paramName, filterCriterion.getValue());

        }
        return null;
    }


    protected void setParameter(String paramName, Object paramValue){
        params.put(paramName, paramValue);
    }
}
