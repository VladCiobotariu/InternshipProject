package com.ozius.internship.project.service.queries.filter;

public enum Operation {
    EQ("="),
    GT(">"),
    GTE(">="),
    LT("<"),
    LTE("<="),
    CONTAINS("like"), // % sdas %
    STARTS_WITH("like"), // sdsa %
    ENDS_WITH("like"); // % dsad


    private final String sqlOperator;

    Operation(String sqlOperator) {
        this.sqlOperator = sqlOperator;
    }

    public String getSqlOperator() {
        return sqlOperator;
    }
}
