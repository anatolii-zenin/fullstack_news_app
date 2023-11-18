package com.mjc.school.service.dto.page;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PageDTOResp<T> {
    private List<T> data;
    private int currentPage;
    private int totalPages;
    private boolean isLastPage;
}
