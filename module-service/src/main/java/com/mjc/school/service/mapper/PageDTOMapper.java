package com.mjc.school.service.mapper;

import com.mjc.school.repository.page.PageParams;
import com.mjc.school.service.dto.page.PageDTOReq;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
@Component
public abstract class PageDTOMapper {
    public abstract PageParams pageReqToPageParams(PageDTOReq req);
}
