package ru.danilenkoya.linkShortener.service;

import ru.danilenkoya.linkShortener.dto.CreateLinkInfoRequest;

public interface LinkInfoService {

    String getShortLink(CreateLinkInfoRequest request);
}
