package com.ozius.internship.project.service.queries;

import java.util.HashMap;
import java.util.Map;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;

public abstract class QueryBuilder {

    protected final StringBuilder queryBuilder;
    protected final Map<String, Object> params;

    private boolean haveAddedAnyConditions = false;

    public QueryBuilder(String query) {
        this.queryBuilder = new StringBuilder(query);
        this.params = new HashMap<>();
    }

    public QueryBuilder and(String condition, String paramName, Object paramValue){
        if(isEmpty(paramValue)) {
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

    protected QueryBuilder setParameter(String paramName, Object paramValue){
        params.put(paramName, paramValue);

        return this;
    }
}
