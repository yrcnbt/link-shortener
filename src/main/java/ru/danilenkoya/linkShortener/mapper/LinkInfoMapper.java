package ru.danilenkoya.linkShortener.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import ru.danilenkoya.linkShortener.dto.CreateLinkInfoRequest;
import ru.danilenkoya.linkShortener.model.LinkInfo;

@Mapper(componentModel = "spring")
@Component
public interface LinkInfoMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "shortLink", ignore = true)
    @Mapping(target = "openingCount", ignore = true)
    LinkInfo toLinkInfo(CreateLinkInfoRequest source);
}
