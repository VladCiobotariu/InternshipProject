package com.ozius.internship.project.service.queries;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.stream.Collectors;

public abstract class JpaQueryBuilder<E,R> extends QueryBuilder {

    private final EntityManager em;
    private final Class<E> queryResultClassType;


    public JpaQueryBuilder(String query, EntityManager em, Class<E> queryResultClassType) {
        super(query);
        this.em = em;
        this.queryResultClassType = queryResultClassType;
    }

    public List<R> getResultList(){
        TypedQuery<E> query = em.createQuery(queryBuilder.toString(), queryResultClassType);

        for (String paramName : params.keySet()) {
            query.setParameter(paramName, params.get(paramName));
        }

        ResultTransformer<E,R> resultTransformer = getTransformer();

        List<E> records = query.getResultList();

        return  records.stream()
                .map(resultTransformer::transform)
                .collect(Collectors.toList());
    }

    public abstract ResultTransformer<E,R> getTransformer();

}
