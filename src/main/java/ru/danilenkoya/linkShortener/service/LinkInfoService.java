package ru.danilenkoya.linkShortener.service;

import ru.danilenkoya.linkShortener.dto.CreateLinkInfoRequest;
import ru.danilenkoya.linkShortener.dto.LinkInfoResponse;
import ru.danilenkoya.linkShortener.model.LinkInfo;

import java.util.List;
import java.util.UUID;

public interface LinkInfoService {

    LinkInfoResponse createShortLink(CreateLinkInfoRequest request);

    LinkInfoResponse findByShortLink(String shortLink);

    List<LinkInfoResponse> findAll();

    List<LinkInfoResponse> findByFilter();

    LinkInfo removeShortLink(UUID id);
}
