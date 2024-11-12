package ru.danilenkoya.linkShortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.danilenkoya.loggingstarter.LoggingStarterAutoConfiguration;


@SpringBootApplication
public class LinkShortenerApp {

    public static void main(String[] args) {
        LoggingStarterAutoConfiguration.println("hello");
        SpringApplication.run(LinkShortenerApp.class, args);
    }
}
