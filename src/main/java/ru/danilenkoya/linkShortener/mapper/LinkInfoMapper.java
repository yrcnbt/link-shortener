package ru.danilenkoya.linkShortener.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.danilenkoya.linkShortener.dto.CreateLinkInfoRequest;
import ru.danilenkoya.linkShortener.dto.LinkInfoResponse;
import ru.danilenkoya.linkShortener.model.LinkInfo;

@Mapper(componentModel = "spring")
public interface LinkInfoMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "shortLink", ignore = true)
    @Mapping(target = "openingCount", constant = "0L")
    LinkInfo toLinkInfo(CreateLinkInfoRequest source);

    LinkInfoResponse toLinkInfoResponse(LinkInfo linkInfo);
}
