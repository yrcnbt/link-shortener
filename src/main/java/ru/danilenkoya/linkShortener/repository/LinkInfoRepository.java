package ru.danilenkoya.linkShortener.repository;

import ru.danilenkoya.linkShortener.model.LinkInfo;

import java.util.List;
import java.util.Optional;

public interface LinkInfoRepository {

    Optional<LinkInfo> findByShortLink(String shortLink);

    LinkInfo save(LinkInfo linkInfo);

    List<LinkInfo> findAll();

    LinkInfo remove(String shortLink);
}
