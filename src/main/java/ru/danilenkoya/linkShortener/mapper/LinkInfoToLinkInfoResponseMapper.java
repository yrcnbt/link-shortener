package ru.danilenkoya.linkShortener.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.danilenkoya.linkShortener.dto.LinkInfoResponse;
import ru.danilenkoya.linkShortener.model.LinkInfo;

@Mapper(componentModel = "spring")

@Component
public interface LinkInfoToLinkInfoResponseMapper {

    LinkInfoResponse toLinkInfoResponse(LinkInfo linkInfo);
}
