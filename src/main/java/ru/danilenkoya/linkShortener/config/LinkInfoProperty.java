package ru.danilenkoya.linkShortener.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "link-shortener")
@Configuration
@Setter
@Getter
public class LinkInfoProperty {

    private Integer linkLength;

}
