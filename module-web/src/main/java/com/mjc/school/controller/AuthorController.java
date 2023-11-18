package com.mjc.school.controller;

import com.mjc.school.service.dto.author.AuthorDTOReq;
import com.mjc.school.service.dto.author.AuthorDTOResp;

public interface AuthorController extends BaseController<AuthorDTOReq, AuthorDTOResp, Long> {
    AuthorDTOResp readByNewsId(Long newsId);
}
