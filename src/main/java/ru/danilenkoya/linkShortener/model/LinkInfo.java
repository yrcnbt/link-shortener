package ru.danilenkoya.linkShortener.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LinkInfo {
    private UUID id;
    private String shortLink;
    private String link;
    private LocalDateTime endTime;
    private String description;
    private Boolean active;
    private Long openingCount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinkInfo linkInfo = (LinkInfo) o;
        return Objects.equals(id, linkInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
