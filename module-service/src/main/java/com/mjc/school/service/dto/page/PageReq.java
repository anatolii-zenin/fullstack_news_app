package com.mjc.school.service.dto.page;

import com.mjc.school.service.dto.filter.FilterReqDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@Getter
@Setter
public class PageReq {
    private Integer pageNum;
    private Integer pageSize;
    private String sortedBy;
    private String order;
    private List<FilterReqDTO> filters;

    public PageReq(Integer pageNum, Integer pageSize, String sortedBy, String order) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.sortedBy = sortedBy;
        this.order = order;
    }

    public Pageable getPageable() {
        int page = pageNum != null ? pageNum-1 : 0;
        int size = pageSize != null ? pageSize : 10;
        String sortBy = sortedBy != null ? sortedBy : "id";
        Sort.Direction sortOrder = order.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        return PageRequest.of(page, size, sortOrder, sortBy);
    }
}