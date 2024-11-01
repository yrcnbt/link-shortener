package ru.danilenkoya.linkShortener.service;

import ru.danilenkoya.linkShortener.dto.CreateLinkInfoRequest;
import ru.danilenkoya.linkShortener.dto.LinkInfoResponse;

import java.util.List;

public interface LinkInfoService {

    LinkInfoResponse createShortLink(CreateLinkInfoRequest request);

    LinkInfoResponse findByShortLink(String shortLink);

    List<LinkInfoResponse> findAll();

    List<LinkInfoResponse> findByFilter();
}
