package com.mjc.school.controller;

import com.mjc.school.service.dto.tag.TagDTOReq;
import com.mjc.school.service.dto.tag.TagDTOResp;

import java.util.List;

public interface TagController extends BaseController<TagDTOReq, TagDTOResp, Long> {
    List<TagDTOResp> readByNewsId(Long id);
}
