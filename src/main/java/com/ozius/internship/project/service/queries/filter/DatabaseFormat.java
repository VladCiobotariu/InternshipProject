package com.ozius.internship.project.service.queries.filter;

public enum DatabaseFormat {
    FULL_UPPER_CASE,
    FULL_LOWER_CASE,
    FIRST_LETTER_UPPER_CASE,
    USER_INPUT;

    public String applyFormat(String value){
        if(this.equals(FULL_LOWER_CASE)){
            return value.toLowerCase();
        } else if (this.equals(FULL_UPPER_CASE)){
            return value.toUpperCase();
        } else if (this.equals(FIRST_LETTER_UPPER_CASE)){
            return value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();
        } else {
            return value;
        }

    }
}
