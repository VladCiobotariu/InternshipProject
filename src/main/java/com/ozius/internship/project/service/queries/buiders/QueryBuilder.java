package com.ozius.internship.project.service.queries.buiders;

import com.ozius.internship.project.service.queries.filter.FilterCriteria;
import com.ozius.internship.project.service.queries.filter.FilterSpecs;
import com.ozius.internship.project.service.queries.filter.Operation;
import com.ozius.internship.project.service.queries.sort.SortCriteria;
import com.ozius.internship.project.service.queries.sort.SortOrder;
import com.ozius.internship.project.service.queries.sort.SortSpecs;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;

public class QueryBuilder {

    protected final StringBuilder sqlQueryBuilder;
    protected final Map<String, Object> params;

    private final Map<String, String> criteriaToPropertyPathMappings = new HashMap<>();

    private boolean appendWhere = true;
    private boolean haveAddedAnyConditions = false;
    private boolean haveAnyOrderConditions = false;


    public QueryBuilder(String query) {
        this.sqlQueryBuilder = new StringBuilder(query);
        this.params = new HashMap<>();
    }

    public QueryBuilder(String query, boolean appendWhere) {
        this.sqlQueryBuilder = new StringBuilder(query);
        this.params = new HashMap<>();
        this.appendWhere = appendWhere;
    }

    public QueryBuilder and(String condition, String paramName, Object paramValue){
        if(isEmpty(paramValue)){
            return this;
        }
        and(condition);
        setParameter(paramName, paramValue);

        return this;
    }

    public QueryBuilder and(String condition) {
        if(haveAddedAnyConditions){
            sqlQueryBuilder.append(String.format(" and %s", condition));
        } else {
            if(appendWhere) {
                sqlQueryBuilder.append(" where ");
            }
            sqlQueryBuilder.append(condition);
            haveAddedAnyConditions = true;
        }
        return this;
    }

    public QueryBuilder and(QueryBuilder queryToAppend) {
        String content = queryToAppend.sqlQueryBuilder.toString(); // and (filter = val1 or filter = val 2 or...) and filter=val
        and(content);

        params.putAll(queryToAppend.params);
        return this;
    }

    public QueryBuilder or(String condition, String paramName, Object paramValue) {
        if(isEmpty(paramValue)){
            return this;
        }
        if(haveAddedAnyConditions){
            sqlQueryBuilder.append(String.format(" or %s", condition));
        } else {
            if(appendWhere) {
                sqlQueryBuilder.append(" where ");
            }
            sqlQueryBuilder.append(condition);
            haveAddedAnyConditions = true;
        }
        setParameter(paramName, paramValue);
        return this;
    }

    public QueryBuilder append(String content) {
        sqlQueryBuilder.append(content);
        return this;
    }

    public void orderBy(String orderCondition, SortOrder sortOrder) {
        if(haveAnyOrderConditions) {
            sqlQueryBuilder.append(String.format(" , %s %s", orderCondition, sortOrder));
        } else {
            sqlQueryBuilder.append(String.format(" order by %s %s", orderCondition, sortOrder));
            haveAnyOrderConditions = true;
        }
    }

    protected void mapCriteriaToPropertyPath(String criteria, String propertyPath) {
        criteriaToPropertyPathMappings.put(criteria, propertyPath);
    }

    public void applySortSpecs(SortSpecs sortSpecs) {
        for (SortCriteria sortCriterion : sortSpecs.getSortCriteria()) {
            String propertyPath = criteriaToPropertyPathMappings.get(sortCriterion.getCriteria());

            if(propertyPath == null) {
                throw new IllegalArgumentException("Unmapped criteria " + sortCriterion.getCriteria());
            }
            orderBy(propertyPath, sortCriterion.getSortOrder());
        }
    }

    /***
     * Build where conditions based on search criteria.
     *
     * example:
     * Input:
     * ProductName = 'rosii'
     * Price > '12'
     * => where p.name = 'rosii' and p.price > 12
     *
     *
     * example:
     * Input:
     * ProductName = 'rosii'
     * productName like '12'
     * categoryName = 'fructe'
     * categoryName = legume'
     * => where p.name = ('rosii' or p.name like '%mere%') and (p.categoryName = 'fructe' or p.categoryName = 'legume')
     *
     * @param filterSpecs
     */

    public void applyFilterSpecs(FilterSpecs filterSpecs) {
        int paramIndex = 0;
        Map<String, Set<FilterCriteria>> filterCriteriaByFilterName = filterSpecs.getFilterCriteria().stream()
                .collect(Collectors.groupingBy(FilterCriteria::getCriteria, Collectors.toSet()));

        buildOrCondition(filterCriteriaByFilterName, paramIndex);
    }

    private void buildOrCondition(Map<String, Set<FilterCriteria>> filterCriteriaByFilterName, int paramIndex) {
        for(Map.Entry<String, Set<FilterCriteria>> entry : filterCriteriaByFilterName.entrySet() ) {
            String filterName = entry.getKey();
            Set<FilterCriteria> filterCriteriaForFilter = entry.getValue();

            /**
             * Builds condition for a certain filter and set of criterias
             * example:
             * (filter1 = val1 or filter1 like val2 or filter1=val3)
             *
             * example:
             * filter 1 = val1 --- for a single filter criteria
             **/

            QueryBuilder filterConditionBuilder = setStartOrQuery(filterCriteriaForFilter.size()) ;

            addOrConditionsToBuilder(filterCriteriaForFilter, filterName, paramIndex, filterConditionBuilder);

            filterConditionBuilder = setEndOrQuery(filterCriteriaForFilter.size(), filterConditionBuilder);

            and(filterConditionBuilder);
        }
    }

    private void addOrConditionsToBuilder(Set<FilterCriteria> filterCriteriaForFilter, String filterName, int paramIndex, QueryBuilder filterConditionBuilder) {
        for (FilterCriteria filterCriterion : filterCriteriaForFilter) {

            String propertyPath = criteriaToPropertyPathMappings.get(filterName);
            Operation operation = filterCriterion.getOperation();
            String sqlOperator = operation.getSqlOperator();
            Object value = filterCriterion.getValue();

            if(propertyPath == null) {
                throw new IllegalArgumentException("Unmapped criteria " + filterName);
            }

            Object newValue = prepareSQLValue(value, operation);

            String paramName = String.format("%s%s", filterName, paramIndex++);

            String condition = String.format("%s %s :%s", propertyPath, sqlOperator, paramName);

            filterConditionBuilder.or(condition, paramName, newValue);
        }
    }

    private Object prepareSQLValue(Object value, Operation operation) {
        if(operation.equals(Operation.CONTAINS)) {
            return String.format("%%%s%%", value);
        }
        if(operation.equals(Operation.STARTS_WITH)) {
            return String.format("%s%%", value);
        }
        if(operation.equals(Operation.ENDS_WITH)) {
            return String.format("%%%s", value);
        }
        return value;
    }

    private QueryBuilder setStartOrQuery(int filterCriteriaFilterSize) {
        String openingBracket = (filterCriteriaFilterSize > 1) ? "(" : "";
        return new QueryBuilder(openingBracket, false);
    }

    private QueryBuilder setEndOrQuery(int filterCriteriaFilterSize, QueryBuilder filterConditionBuilder) {
        String closingBracket = (filterCriteriaFilterSize > 1) ? ") " : "";
        return filterConditionBuilder.append(closingBracket);
    }

    protected void setParameter(String paramName, Object paramValue){
        params.put(paramName, paramValue);
    }
}
