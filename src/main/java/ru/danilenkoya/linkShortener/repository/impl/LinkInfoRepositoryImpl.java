package ru.danilenkoya.linkShortener.repository.impl;

import org.springframework.stereotype.Repository;
import ru.danilenkoya.linkShortener.model.LinkInfo;
import ru.danilenkoya.linkShortener.repository.LinkInfoRepository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class LinkInfoRepositoryImpl implements LinkInfoRepository {

    private final Map<String, LinkInfo> linksCahce = new ConcurrentHashMap<>();

    /**
     * @param shortLink
     * @return
     */
    @Override
    public LinkInfo findByShortLink(String shortLink) {
        return linksCahce.get(shortLink);
    }

    /**
     * @param linkInfo
     * @return
     */
    @Override
    public LinkInfo save(LinkInfo linkInfo) {
        linkInfo.setId(UUID.randomUUID());
        return linksCahce.put(linkInfo.getShortLink(), linkInfo);
    }

    /**
     * @return
     */
    @Override
    public List<LinkInfo> findAll() {
        return (List<LinkInfo>) linksCahce.values();
    }
}
