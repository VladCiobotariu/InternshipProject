package com.ozius.internship.project.service.queries;

public interface ResultTransformer<I,O> {
    O transform(I input);
}
