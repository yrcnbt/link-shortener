package ru.danilenkoya.linkShortener.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import ru.danilenkoya.linkShortener.annotation.LogExecutionTime;
import ru.danilenkoya.linkShortener.config.LinkInfoProperty;
import ru.danilenkoya.linkShortener.dto.CreateLinkInfoRequest;
import ru.danilenkoya.linkShortener.dto.LinkInfoResponse;
import ru.danilenkoya.linkShortener.dto.UpdateLinkInfoRequest;
import ru.danilenkoya.linkShortener.exception.LinkShortenerException;
import ru.danilenkoya.linkShortener.exception.NotFoundException;
import ru.danilenkoya.linkShortener.mapper.LinkInfoMapper;
import ru.danilenkoya.linkShortener.model.LinkInfo;
import ru.danilenkoya.linkShortener.repository.LinkInfoRepository;
import ru.danilenkoya.linkShortener.service.LinkInfoService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class LinkInfoServiceImpl implements LinkInfoService  {
    private final LinkInfoProperty linkInfoProperty;
    private final LinkInfoRepository linkInfoRepository;
    private final LinkInfoMapper linkInfoMapper;


    @Override
    @LogExecutionTime
    public LinkInfoResponse createShortLink(CreateLinkInfoRequest request) {
        LinkInfo linkInfo = linkInfoMapper.toLinkInfo(request);
        linkInfo.setShortLink(RandomStringUtils.randomAlphabetic(linkInfoProperty.getLinkLength()));
        return linkInfoMapper.toLinkInfoResponse(linkInfoRepository.save(linkInfo));
    }

    /**
     * @param shortLink короткая ссылка
     * @return LinkInfoResponse возвращает информацию о ссылке по короткой ссылке
     */
    @Override
    @LogExecutionTime
    public LinkInfoResponse findByShortLink(String shortLink) {
      LinkInfo linkInfo = linkInfoRepository.findByShortLink(shortLink)
                .orElseThrow(() -> new NotFoundException("Not found LinkInfo by shortLink: " + shortLink));
        if(linkInfo.getActive() && LocalDateTime.now().isBefore(linkInfo.getEndTime())) {
            return linkInfoMapper
                    .toLinkInfoResponse(linkInfo);
        } else {
            throw new LinkShortenerException("Короткая ссылка " + shortLink + " не активна или у нее истек срок действия");
        }
    }

    /**
     * @return List<LinkInfoResponse> возвращает все ссылки с информацией о них
     */
    @Override
    @LogExecutionTime
    public List<LinkInfoResponse> findAll() {
        return linkInfoRepository.findAll().stream()
                .map(linkInfoMapper::toLinkInfoResponse)
                .collect(Collectors.toList());
    }

    /**
     * @return возвращает отфильтрованные ссылки с информацией о них
     */
    @Override
    @LogExecutionTime
    public List<LinkInfoResponse> findByFilter(String description) {
        return linkInfoRepository.findAll().stream()
                .filter(linkInfo -> description.equals(linkInfo.getDescription()))
                .map(linkInfoMapper::toLinkInfoResponse)
                .toList();
    }

    /**
     * @param id идентификатор ссылки
     * @return инфа об удаленной ссылке
     */
    @Override
    @LogExecutionTime
    public LinkInfoResponse deleteById(UUID id) {
        return linkInfoMapper
                .toLinkInfoResponse(linkInfoRepository.deleteById(id)) ;
    }

    /**
     * @param request
     * @return обновленная инфа о ссылке
     */
    @Override
    @LogExecutionTime
    public LinkInfoResponse updateLinkInfo(UpdateLinkInfoRequest request) {
        LinkInfo oldLinkInfo = linkInfoRepository.findById(request.getId());
        if (Objects.nonNull(request.getLink())) {
            oldLinkInfo.setLink(request.getLink());
        }
        if (Objects.nonNull(request.getActive())) {
            oldLinkInfo.setActive(request.getActive());
        }
        if (Objects.nonNull(request.getDescription())) {
            oldLinkInfo.setDescription(request.getDescription());
        }
        oldLinkInfo.setEndTime(request.getEndTime());
      return linkInfoMapper.toLinkInfoResponse(linkInfoRepository.save(oldLinkInfo));
    }


}
