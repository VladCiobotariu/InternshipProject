package com.ozius.internship.project.service.queries.buiders;

import com.ozius.internship.project.service.queries.transformers.ResultTransformer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.stream.Collectors;

public abstract class JpaQueryBuilder<E,R> extends QueryBuilder {

    protected final EntityManager em;
    protected final Class<E> queryResultClassType;


    public JpaQueryBuilder(String query, EntityManager em, Class<E> queryResultClassType) {
        super(query);
        this.em = em;
        this.queryResultClassType = queryResultClassType;
    }

    public List<R> getResultList(){
        TypedQuery<E> query = buildQuery();

        return getTransformResultList(query);
    }

    protected TypedQuery<E> buildQuery() {
        @SuppressWarnings("SqlSourceToSinkFlow")
        TypedQuery<E> query = em.createQuery(sqlQueryBuilder.toString(), queryResultClassType);

        for (String paramName : params.keySet()) {
            query.setParameter(paramName, params.get(paramName));
        }
        return query;
    }

    protected List<R> getTransformResultList(TypedQuery<E> query) {
        ResultTransformer<E,R> resultTransformer = getTransformer();

        List<E> records = query.getResultList();

        return  records.stream()
                .map(resultTransformer::transform)
                .collect(Collectors.toList());
    }

    public abstract ResultTransformer<E,R> getTransformer();

}
