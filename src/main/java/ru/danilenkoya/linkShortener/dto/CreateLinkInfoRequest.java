package ru.danilenkoya.linkShortener.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateLinkInfoRequest {
    private String link;
    private LocalDateTime endTime;
    private String description;
    private Boolean active;
}
