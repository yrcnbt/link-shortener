package ru.danilenkoya.linkShortener.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import ru.danilenkoya.linkShortener.dto.CreateLinkInfoRequest;
import ru.danilenkoya.linkShortener.dto.LinkInfoResponse;
import ru.danilenkoya.linkShortener.service.LinkInfoService;

import java.util.List;

@Slf4j
public class LogExecutionTimeLinkInfoServiceProxy implements LinkInfoService {

    private LinkInfoService linkInfoService;

    public LogExecutionTimeLinkInfoServiceProxy(LinkInfoService linkInfoService) {
            this.linkInfoService = linkInfoService;
    }

    @Override
    public LinkInfoResponse createShortLink(CreateLinkInfoRequest request) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            return linkInfoService.createShortLink(request);
        } finally {
            stopWatch.stop();
            log.info("Время выполнения метода createShortLink : " + stopWatch.getTotalTimeMillis());
        }
    }

    @Override
    public LinkInfoResponse findByShortLink(String shortLink) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            return linkInfoService.findByShortLink(shortLink);
        } finally {
            stopWatch.stop();
            log.info("Время выполнения метода findByShortLink : " + stopWatch.getTotalTimeMillis());
        }    }

    @Override
    public List<LinkInfoResponse> findAll() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            return linkInfoService.findAll();
        } finally {
            stopWatch.stop();
            log.info("Время выполнения метода findAll : " + stopWatch.getTotalTimeMillis());
        }    }

    @Override
    public List<LinkInfoResponse> findByFilter() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            return linkInfoService.findByFilter();
        } finally {
            stopWatch.stop();
            log.info("Время выполнения метода findByFilter : " + stopWatch.getTotalTimeMillis());
        }
    }
}
