package com.mjc.school.service.dto.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilterReqDTO {
    private String column;
    private String value;
    private String joinTableName;
    private Operation operation;

    public enum Operation {
        EQUAL, LIKE, JOIN
    }
}
