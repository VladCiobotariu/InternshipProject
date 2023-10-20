package com.ozius.internship.project.service.queries.buiders;

import com.ozius.internship.project.service.queries.transformers.ResultTransformer;
import com.ozius.internship.project.service.queries.sort.SortOrder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public abstract class PagingJpaQueryBuilder<E, R> extends JpaQueryBuilder<E,R> {
    public PagingJpaQueryBuilder(String query, EntityManager em, Class<E> queryResultClassType) {
        super(query, em, queryResultClassType);
    }

    public List<R> getPagingResultList(int itemsPerPage, int page){
        orderBy(orderByDefault(), SortOrder.ASC);

        TypedQuery<E> query = buildQuery()
                .setFirstResult(page * itemsPerPage)
                .setMaxResults(itemsPerPage);

        return getTransformResultList(query);
    }

    public abstract String orderByDefault();

    public abstract ResultTransformer<E,R> getTransformer();
}
