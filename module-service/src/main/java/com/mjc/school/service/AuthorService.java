package com.mjc.school.service;

import com.mjc.school.service.dto.author.AuthorDTOReq;
import com.mjc.school.service.dto.author.AuthorDTOResp;

public interface AuthorService extends PaginationCapableService<AuthorDTOReq, AuthorDTOResp, Long> {
    AuthorDTOResp readAuthorByNewsId(Long newsId);
}
