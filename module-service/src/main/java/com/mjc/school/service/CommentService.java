package com.mjc.school.service;

import com.mjc.school.service.dto.comment.CommentDTOReq;
import com.mjc.school.service.dto.comment.CommentDTOResp;

import java.util.List;

public interface CommentService extends PaginationCapableService<CommentDTOReq, CommentDTOResp, Long> {
    List<CommentDTOResp> readCommentsByNewsId(Long newsId);
}
