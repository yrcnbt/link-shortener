package ru.danilenkoya.linkShortener.service;

import ru.danilenkoya.linkShortener.dto.CreateLinkInfoRequest;
import ru.danilenkoya.linkShortener.dto.LinkInfoResponse;
import ru.danilenkoya.linkShortener.dto.UpdateLinkInfoRequest;
import ru.danilenkoya.linkShortener.model.LinkInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface LinkInfoService {

    LinkInfoResponse createShortLink(CreateLinkInfoRequest request);

    LinkInfoResponse findByShortLink(String shortLink);

    List<LinkInfoResponse> findAll();

    List<LinkInfoResponse> findByFilter(String description);

    LinkInfoResponse deleteById(UUID id);

    LinkInfoResponse updateLinkInfo(UpdateLinkInfoRequest request);

    public static int countPassengers(ArrayList<int[]> stops) {
        return stops.stream().map(arr -> arr[0] - arr[1])
                .reduce(0, (Integer::sum));
    }
}
