package com.ozius.internship.project.service.queries.transformers;

public interface ResultTransformer<I,O> {
    O transform(I input);
}
