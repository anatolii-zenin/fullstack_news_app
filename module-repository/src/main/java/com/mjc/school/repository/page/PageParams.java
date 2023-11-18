package com.mjc.school.repository.page;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PageParams {
    private int pageNum;
    private int pageSize;
    private String sortedBy;
    private String order;
}
