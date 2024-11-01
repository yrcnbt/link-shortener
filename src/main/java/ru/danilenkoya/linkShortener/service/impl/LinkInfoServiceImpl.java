package ru.danilenkoya.linkShortener.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.danilenkoya.linkShortener.dto.CreateLinkInfoRequest;
import ru.danilenkoya.linkShortener.dto.LinkInfoResponse;
import ru.danilenkoya.linkShortener.exception.NotFoundException;
import ru.danilenkoya.linkShortener.mapper.LinkInfoMapper;
import ru.danilenkoya.linkShortener.model.LinkInfo;
import ru.danilenkoya.linkShortener.repository.LinkInfoRepository;
import ru.danilenkoya.linkShortener.service.LinkInfoService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class LinkInfoServiceImpl implements LinkInfoService {

    @Value("${link-shortener.link-length}")
    private Integer linkLength;
    private final LinkInfoRepository linkInfoRepository;
    private final LinkInfoMapper linkInfoMapper;

    @Override
    public LinkInfoResponse createShortLink(CreateLinkInfoRequest request) {
        LinkInfo linkInfo = linkInfoMapper.toLinkInfo(request);
        linkInfo.setShortLink(RandomStringUtils.randomAlphabetic(linkLength));
        return linkInfoMapper.toLinkInfoResponse(linkInfoRepository.save(linkInfo));
    }

    /**
     * @param shortLink короткая ссылка
     * @return LinkInfoResponse возвращает информацию о ссылке по короткой ссылке
     */
    @Override
    public LinkInfoResponse findByShortLink(String shortLink) {
        Optional<LinkInfo> linkInfo = linkInfoRepository.findByShortLink(shortLink);
        return linkInfoMapper
                .toLinkInfoResponse(linkInfo.orElseThrow(() -> new NotFoundException("Not found LinkInfo")));
    }

    /**
     * @return List<LinkInfoResponse> возвращает все ссылки с информацией о них
     */
    @Override
    public List<LinkInfoResponse> findAll() {
        return linkInfoRepository.findAll().stream()
                .map(linkInfoMapper::toLinkInfoResponse)
                .collect(Collectors.toList());
    }

    /**
     * @return возвращает отфильтрованные ссылки с информацией о них
     */
    @Override
    public List<LinkInfoResponse> findByFilter() {
        return null;
    }


}
