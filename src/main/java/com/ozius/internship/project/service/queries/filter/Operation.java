package com.ozius.internship.project.service.queries.filter;

public enum Operation {
    EQUALS("="),
    GREATER_THAN(">"),
    GREATER_OR_EQUALS_THAN(">="),
    LESS_THAN("<"),
    LESS_OR_EQUALS_THAN("<=");

    private String sqlOperator;

    Operation(String sqlOperator) {
        this.sqlOperator = sqlOperator;
    }

    public String getSqlOperator() {
        return sqlOperator;
    }
}
