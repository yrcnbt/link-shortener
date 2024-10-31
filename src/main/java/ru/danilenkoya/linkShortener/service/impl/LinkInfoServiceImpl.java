package ru.danilenkoya.linkShortener.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Service;
import ru.danilenkoya.linkShortener.dto.CreateLinkInfoRequest;
import ru.danilenkoya.linkShortener.dto.LinkInfoResponse;
import ru.danilenkoya.linkShortener.exception.NotFoundException;
import ru.danilenkoya.linkShortener.mapper.LinkInfoMapper;
import ru.danilenkoya.linkShortener.mapper.LinkInfoToLinkInfoResponseMapper;
import ru.danilenkoya.linkShortener.model.LinkInfo;
import ru.danilenkoya.linkShortener.repository.LinkInfoRepository;
import ru.danilenkoya.linkShortener.service.LinkInfoService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class LinkInfoServiceImpl implements LinkInfoService {

    @Value("${link-shortener.link-length}")
    private Integer linkLength;
    private final LinkInfoRepository linkInfoRepository;
    @Autowired
    private final LinkInfoMapper linkInfoMapper;
    @Autowired
    private final LinkInfoToLinkInfoResponseMapper linkInfoResponseMapper;

    @Override
    public LinkInfoResponse getShortLink(CreateLinkInfoRequest request) {
        LinkInfo linkInfo = linkInfoMapper.toLinkInfo(request);
        linkInfo.setShortLink(RandomStringUtils.randomAlphabetic(linkLength));
        return linkInfoResponseMapper.toLinkInfoResponse(linkInfoRepository.save(linkInfo));
    }

    /**
     * @param shortLink
     * @return LinkInfoResponse
     */
    @Override
    public LinkInfoResponse findByShortLink(String shortLink) {
        LinkInfo linkInfo = linkInfoRepository.findByShortLink(shortLink);
        if (Objects.nonNull(linkInfo)) {
            return linkInfoResponseMapper.toLinkInfoResponse(linkInfo);
        } else {
            throw new NotFoundException("Not found LinkInfo");
        }

    }

    /**
     * @return List<LinkInfoResponse>
     */
    @Override
    public List<LinkInfoResponse> findAll() {
        return linkInfoRepository.findAll().stream()
                .map(linkInfoResponseMapper::toLinkInfoResponse)
                .collect(Collectors.toList());
    }

    /**
     * @return
     */
    @Override
    public List<LinkInfoResponse> findByFilter() {
        return null;
    }


}
