package ru.danilenkoya.linkShortener.service.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.danilenkoya.linkShortener.dto.CreateLinkInfoRequest;
import ru.danilenkoya.linkShortener.service.LinkInfoService;

import java.util.HashMap;
import java.util.Map;

@Service
public class LinkInfoServiceInfo implements LinkInfoService {

    @Value("${link-length}")
    private Integer linkLength;
    private final Map<String, CreateLinkInfoRequest> resultMap = new HashMap<>();

    @Override
    public String getShortLink(CreateLinkInfoRequest request) {
        String linkShort = RandomStringUtils.randomAlphabetic(linkLength);
        resultMap.put(linkShort, request);
        return linkShort;
    }
}
