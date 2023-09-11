package com.ozius.internship.project.service.queries;

import org.modelmapper.ModelMapper;

public class ModelMapperBasedResultTransformer<I,O> implements ResultTransformer<I,O> {

    private final ModelMapper modelMapper;
    private final Class<O> outputClass;

    public ModelMapperBasedResultTransformer(ModelMapper modelMapper, Class<O> outputClass) {
        this.modelMapper = modelMapper;
        this.outputClass = outputClass;
    }

    @Override
    public O transform(I input) {
        return modelMapper.map(input, outputClass);
    }
}
