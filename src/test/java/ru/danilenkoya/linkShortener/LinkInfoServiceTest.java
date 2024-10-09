package ru.danilenkoya.linkShortener;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import ru.danilenkoya.linkShortener.dto.CreateLinkInfoRequest;
import ru.danilenkoya.linkShortener.service.LinkInfoService;

import java.time.LocalDateTime;

@SpringBootTest
public class LinkInfoServiceTest {

    @Value("${link-length}")
    private Integer linkLength;
    @Autowired
    private LinkInfoService linkInfoService;

    @Test
    void getShortLinkHappy() {
        String fullLink = RandomStringUtils.randomAlphabetic(linkLength * 2);
        CreateLinkInfoRequest request = CreateLinkInfoRequest.builder()
                .link(fullLink)
                .active(true)
                .endTime(LocalDateTime.now().plusYears(1))
                .description("This is link!!!")
                .build();

        String result = linkInfoService.getShortLink(request);

        System.out.println(result);
    }
}
