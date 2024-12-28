package ru.danilenkoya.linkShortener.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.danilenkoya.linkShortener.dto.LinkInfoResponse;
import ru.danilenkoya.linkShortener.service.LinkInfoService;

@RequestMapping("api/v1/short-link")
@RestController
@RequiredArgsConstructor
public class ShortLinkController {

    private final LinkInfoService linkInfoService;

    @GetMapping("/{shortLink}")
    public ResponseEntity<String> getShortLink(@PathVariable String shortLink) {
        LinkInfoResponse linkInfoResponse = linkInfoService.findByShortLink(shortLink);
        ResponseEntity response = ResponseEntity
                .status(HttpStatus.TEMPORARY_REDIRECT)
                .header(HttpHeaders.LOCATION, linkInfoResponse.getLink())
                .build();
        return response;
    }


}
