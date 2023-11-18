package com.mjc.school.service.dto.tag;

import com.mjc.school.service.dto.Request;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TagDTOReq extends Request<Long> {
    private Long id;
    private String name;
}
