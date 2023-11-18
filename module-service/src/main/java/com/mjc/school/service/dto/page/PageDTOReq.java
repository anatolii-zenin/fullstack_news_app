package com.mjc.school.service.dto.page;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PageDTOReq {
    private int pageNum;
    private int pageSize;
    private String sortedBy;
    private String order;
}
