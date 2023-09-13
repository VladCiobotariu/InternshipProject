package com.ozius.internship.project.service.queries.filter;

import com.ozius.internship.project.service.queries.sort.SortSpecifications;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterSpecificationsParser {

    /***
     *
     * @param filterText - filter text to be parsed, in format: priceFrom>=12,city=Timisoara,categoryName=fruits
     */

    public static FilterSpecifications parse(String filterText) {

        FilterSpecifications filterSpecifications = new FilterSpecifications();
        List<String> criteriaList = new ArrayList<>(List.of(filterText.split(",")));

        for (String criterio : criteriaList) {

            Pattern pattern = Pattern.compile("([^=><]+)([=><]+)([^=><]+)");
            Matcher matcher = pattern.matcher(criterio.trim());

            if(matcher.matches()) {
                String criteria = matcher.group(1).trim();
                String operationStr = matcher.group(2).trim();
                String valueString = matcher.group(3).trim();

                Operation operation = getOperationFromSymbol(operationStr);

                if (operation != null) {
                    Object value;
                    try {
                        if(valueString.contains(".")) {
                            value = Double.parseDouble(valueString);
                        } else {
                            value = Integer.parseInt(valueString);
                        }
                    } catch (NumberFormatException e) {
                        value = valueString;
                    }

                    FilterCriteria filterCriteria = new FilterCriteria(criteria, operation, value);
                    filterSpecifications.addFilterCriteria(filterCriteria);
                } else {
                    throw new IllegalArgumentException("This operation does not exist: " + operationStr);
                }

            } else {
                throw new IllegalArgumentException("Invalid filter criteria " + criterio);
            }

        }
        return filterSpecifications;
    }

    private static Operation getOperationFromSymbol(String symbol) {
        switch (symbol) {
            case "=":
                return Operation.EQUALS;
            case ">":
                return Operation.GREATER_THAN;
            case ">=":
                return Operation.GREATER_OR_EQUALS_THAN;
            case "<":
                return Operation.LESS_THAN;
            case "<=":
                return Operation.LESS_OR_EQUALS_THAN;
            default:
                return null;
        }
    }

}
