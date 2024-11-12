package ru.danilenkoya.linkShortener.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LinkInfoResponse {
    private UUID id;
    private String shortLink;
    private String link;
    private LocalDateTime endTime;
    private String description;
    private Boolean active;
    private Long openingCount;
}
