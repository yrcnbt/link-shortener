package ru.danilenkoya.linkShortener.repository.impl;

import org.springframework.stereotype.Repository;
import ru.danilenkoya.linkShortener.model.LinkInfo;
import ru.danilenkoya.linkShortener.repository.LinkInfoRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class LinkInfoRepositoryImpl implements LinkInfoRepository {

    private final Map<String, LinkInfo> linksCahce = new ConcurrentHashMap<>();

    /**
     * @param shortLink возвращает информацию о ссылке по короткой ссылке
     * @return
     */
    @Override
    public Optional<LinkInfo> findByShortLink(String shortLink) {
        return Optional.of(linksCahce.get(shortLink));
    }

    /**
     * @param linkInfo сохраняет информацию о ссылке
     * @return возвращает сохраненную ссылку
     */
    @Override
    public LinkInfo save(LinkInfo linkInfo) {
        linkInfo.setId(UUID.randomUUID());
        linksCahce.put(linkInfo.getShortLink(), linkInfo);
        return linkInfo;
    }

    /**
     * @return возвращает все имеющиеся ссылки с информацией о них
     */
    @Override
    public List<LinkInfo> findAll() {
        return new ArrayList<>(linksCahce.values());
    }

    /**
     * @return
     */
    @Override
    public LinkInfo remove(String shortLink) {
        return linksCahce.remove(shortLink);
    }


}
