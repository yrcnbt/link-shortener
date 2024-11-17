package ru.danilenkoya.linkShortener.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.danilenkoya.linkShortener.mapper.LinkInfoMapper;
import ru.danilenkoya.linkShortener.mapper.LinkInfoMapperImpl;
import ru.danilenkoya.linkShortener.repository.LinkInfoRepository;
import ru.danilenkoya.linkShortener.service.LinkInfoService;
import ru.danilenkoya.linkShortener.service.impl.LinkInfoServiceImpl;
import ru.danilenkoya.linkShortener.service.impl.LogExecutionTimeLinkInfoServiceProxy;

@Configuration
public class LinkShortenerConfig {
    @Autowired
    private LinkInfoRepository linkInfoRepository;
    @Autowired
    private LinkInfoProperty linkInfoProperty;

    @Bean
    LinkInfoMapper linkInfoMapper() {
        return new LinkInfoMapperImpl();
    }

    @Bean
    public LinkInfoService linkInfoService() {
        LinkInfoServiceImpl linkInfoServiceImpl = new LinkInfoServiceImpl(linkInfoProperty, linkInfoRepository, linkInfoMapper());
        return new LogExecutionTimeLinkInfoServiceProxy(linkInfoServiceImpl);
    }
}
