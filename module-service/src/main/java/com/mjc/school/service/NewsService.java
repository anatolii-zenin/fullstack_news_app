package com.mjc.school.service;

import com.mjc.school.service.dto.news.NewsDTOReq;
import com.mjc.school.service.dto.news.NewsDTOResp;

import java.util.List;

public interface NewsService extends PaginationCapableService<NewsDTOReq, NewsDTOResp, Long>{
    List<NewsDTOResp> readByCriteria(NewsDTOReq req);
}
