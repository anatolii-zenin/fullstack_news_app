package com.mjc.school.service.dto.comment;

import com.mjc.school.service.dto.Request;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTOReq extends Request<Long> {
    private Long id;
    private String content;
    private Long newsId;
}
