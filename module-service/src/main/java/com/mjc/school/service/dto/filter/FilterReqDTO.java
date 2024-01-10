package com.mjc.school.service.dto.filter;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FilterReqDTO {
    private String column;
    private String value;
    private String joinTableName;
    private Operation operation;
    private LogicalOperation logicalOperation;

    private List<FilterReqDTO> subFilters;
    public enum Operation {
        EQUAL, LIKE, JOIN, COMBINE
    }

    public enum LogicalOperation {
        AND, OR
    }
}
