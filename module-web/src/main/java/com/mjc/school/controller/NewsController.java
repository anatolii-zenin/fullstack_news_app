package com.mjc.school.controller;

import com.mjc.school.service.dto.news.NewsDTOReq;
import com.mjc.school.service.dto.news.NewsDTOResp;

import java.util.List;

public interface NewsController extends BaseController<NewsDTOReq, NewsDTOResp, Long> {
    List<NewsDTOResp> readByCriteria(NewsDTOReq req);
}
