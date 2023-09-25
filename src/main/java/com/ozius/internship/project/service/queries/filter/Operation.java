package com.ozius.internship.project.service.queries.filter;

public enum Operation {
    EQ("="),
    GT(">"),
    GTE(">="),
    LT("<"),
    LTE("<="),
    LIKE("like"),
    CONTAINS("like"),
    STARTS_WITH("like"),
    ENDS_WITH("like");


    private String sqlOperator;

    Operation(String sqlOperator) {
        this.sqlOperator = sqlOperator;
    }

    public String getSqlOperator() {
        return sqlOperator;
    }
}
