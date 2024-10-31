package ru.danilenkoya.linkShortener.repository;

import ru.danilenkoya.linkShortener.model.LinkInfo;

import java.util.List;

public interface LinkInfoRepository {

    LinkInfo findByShortLink(String shortLink);

    LinkInfo save(LinkInfo linkInfo);

    List<LinkInfo> findAll();
}
