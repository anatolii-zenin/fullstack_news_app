package com.mjc.school.controller;

import com.mjc.school.service.dto.comment.CommentDTOReq;
import com.mjc.school.service.dto.comment.CommentDTOResp;

import java.util.List;

public interface CommentController extends BaseController<CommentDTOReq, CommentDTOResp, Long> {
    List<CommentDTOResp> readByNewsId(Long id);
}
