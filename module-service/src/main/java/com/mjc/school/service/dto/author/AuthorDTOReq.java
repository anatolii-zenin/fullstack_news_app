package com.mjc.school.service.dto.author;

import com.mjc.school.service.dto.Request;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthorDTOReq extends Request<Long> {
    private Long id;
    private String name;
}
