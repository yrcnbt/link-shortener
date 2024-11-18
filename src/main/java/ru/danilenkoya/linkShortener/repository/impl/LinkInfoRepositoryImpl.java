package ru.danilenkoya.linkShortener.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.danilenkoya.linkShortener.mapper.LinkInfoMapper;
import ru.danilenkoya.linkShortener.model.LinkInfo;
import ru.danilenkoya.linkShortener.repository.LinkInfoRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class LinkInfoRepositoryImpl implements LinkInfoRepository {

    private final Map<String, LinkInfo> linksCahce = new ConcurrentHashMap<>();
    private final LinkInfoMapper linkInfoMapper;
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

    @Override
    public LinkInfo remove(String shortLink) {
        return linksCahce.remove(shortLink);
    }

    @Override
    public LinkInfo findById(UUID id) {
        Map<UUID, LinkInfo> shortLinkMap = linksCahce.values().stream()
                .collect(Collectors.toMap(LinkInfo::getId, linkInfo -> linkInfo));
        return shortLinkMap.get(id);
    }

    @Override
    public LinkInfo deleteById(UUID id) {
        return remove(findById(id).getShortLink());
    }

    /**
     * @param linkInfo
     * @return
     */
    @Override
    public LinkInfo update(LinkInfo linkInfo) {
        LinkInfo oldLinkInfo = findById(linkInfo.getId());
        return linksCahce.put(oldLinkInfo.getShortLink(), linkInfoMapper.update(oldLinkInfo, linkInfo));
    }

}
