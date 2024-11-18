package ru.danilenkoya.linkShortener.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import ru.danilenkoya.linkShortener.dto.CreateLinkInfoRequest;
import ru.danilenkoya.linkShortener.dto.LinkInfoResponse;
import ru.danilenkoya.linkShortener.dto.UpdateLinkInfoRequest;
import ru.danilenkoya.linkShortener.model.LinkInfo;

@Mapper(componentModel = "spring")
public interface LinkInfoMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "shortLink", ignore = true)
    @Mapping(target = "openingCount", constant = "0L")
    LinkInfo toLinkInfo(CreateLinkInfoRequest source);

    @Mapping(target = "shortLink", ignore = true)
    @Mapping(target = "openingCount", ignore = true)
    LinkInfo toLinkInfo(UpdateLinkInfoRequest source);

    LinkInfoResponse toLinkInfoResponse(LinkInfo linkInfo);


    @Mapping(target = "shortLink", source = "shortLink", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "openingCount", source = "openingCount", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    LinkInfo update(@MappingTarget LinkInfo oldLinkInfo, LinkInfo source);
}
