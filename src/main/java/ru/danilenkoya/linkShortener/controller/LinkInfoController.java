package ru.danilenkoya.linkShortener.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.danilenkoya.linkShortener.dto.*;
import ru.danilenkoya.linkShortener.service.LinkInfoService;

import java.util.List;
import java.util.UUID;

@RequestMapping("api/v1/link-info")
@RestController
@RequiredArgsConstructor
@Slf4j
public class LinkInfoController {
    private final LinkInfoService linkInfoService;

    @PostMapping()
    public CommonResponse<LinkInfoResponse> postCreateShortLink(@RequestBody CommonRequest<CreateLinkInfoRequest> request) {
        log.info("Получен запрос на создание короткой ссылки, с телом {}", request.getBody());
        LinkInfoResponse linkInfoResponse = linkInfoService.createShortLink(request.getBody());
        log.info("Короткая ссылка успешно создана, тело ответа: {}", linkInfoResponse);

        return CommonResponse.<LinkInfoResponse>builder()
                .body(linkInfoResponse)
                .build();
    }

    @PutMapping()
    public CommonResponse<LinkInfoResponse> putUpdateShortLink(@RequestBody CommonRequest<UpdateLinkInfoRequest> request) {
        log.info("Получен запрос на обновление короткой ссылки, с телом {}", request.getBody());
        LinkInfoResponse linkInfoResponse = linkInfoService.updateLinkInfo(request.getBody());
        log.info("Короткая ссылка успешно обновлена, тело ответа: {}", linkInfoResponse);

        return CommonResponse.<LinkInfoResponse>builder()
                .body(linkInfoResponse)
                .build();
    }

    @DeleteMapping("/{id}")
    public CommonResponse<LinkInfoResponse> deleteShortLink(@PathVariable UUID id) {
        log.info("Получен запрос на удаление короткой ссылки, с id {}", id);
        LinkInfoResponse linkInfoResponse = linkInfoService.deleteById(id);
        log.info("Короткая ссылка успешно удалена, тело ответа: {}", linkInfoResponse);
        return CommonResponse.<LinkInfoResponse>builder()
                .body(linkInfoResponse)
                .build();
    }

    @GetMapping("/{description}")
    public List<CommonResponse<LinkInfoResponse>> getAllLinkInfoByDescription(@PathVariable String description) {
        log.info("Получен запрос на поиск коротких ссылок по фильтру: {}", description);
        List<LinkInfoResponse> linkInfoResponseList = linkInfoService.findByFilter(description);
        log.info("Успешно получены короткие ссылки в количестве: {}", linkInfoResponseList.size());
        return linkInfoResponseList.stream()
                .map(response -> CommonResponse.<LinkInfoResponse>builder()
                        .body(response)
                        .build())
                .toList();
    }
}
