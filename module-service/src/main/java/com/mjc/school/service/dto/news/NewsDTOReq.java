package com.mjc.school.service.dto.news;

import com.mjc.school.service.dto.Request;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class NewsDTOReq extends Request<Long> {
    private Long id;
    private String title;
    private String content;
    private Long authorId;
    private List<Long> tagIds = new ArrayList<>();
}
