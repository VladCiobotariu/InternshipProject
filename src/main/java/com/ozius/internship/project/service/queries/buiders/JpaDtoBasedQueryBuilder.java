package com.ozius.internship.project.service.queries.buiders;

import com.ozius.internship.project.service.queries.transformers.ResultTransformer;
import jakarta.persistence.EntityManager;

public abstract class JpaDtoBasedQueryBuilder<E> extends JpaQueryBuilder<E,E> {

    public JpaDtoBasedQueryBuilder(String query, EntityManager em, Class<E> queryResultClassType) {
        super(query, em, queryResultClassType);
    }

    public final ResultTransformer<E,E> getTransformer(){
        return input -> input;
    }

}
