package com.mjc.school.service;


import com.mjc.school.service.dto.tag.TagDTOReq;
import com.mjc.school.service.dto.tag.TagDTOResp;

import java.util.List;

public interface TagService extends PaginationCapableService<TagDTOReq, TagDTOResp, Long>{
    List<TagDTOResp> readByNewsId(Long id);
}
