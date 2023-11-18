package com.mjc.school.repository.page;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Page<T> {
    private List<T> data;
    private int currentPage;
    private int totalPages;
    private boolean isLastPage;
    private String sortedBy;
}
