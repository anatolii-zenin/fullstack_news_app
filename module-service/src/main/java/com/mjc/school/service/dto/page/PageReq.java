package com.mjc.school.service.dto.page;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PageReq {
    private int pageNum;
    private int pageSize;
    private String sortedBy;
    private String order;
}