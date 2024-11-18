package ru.danilenkoya.linkShortener;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import ru.danilenkoya.linkShortener.dto.CreateLinkInfoRequest;
import ru.danilenkoya.linkShortener.dto.LinkInfoResponse;
import ru.danilenkoya.linkShortener.dto.UpdateLinkInfoRequest;
import ru.danilenkoya.linkShortener.exception.NotFoundException;
import ru.danilenkoya.linkShortener.model.LinkInfo;
import ru.danilenkoya.linkShortener.repository.LinkInfoRepository;
import ru.danilenkoya.linkShortener.service.LinkInfoService;

import java.time.LocalDateTime;
import java.util.Optional;



@SpringBootTest
@TestPropertySource("classpath:application-test.yml")
public class LinkInfoServiceTest {

    private static final String FULL_LINK = "thisIsFullLink!!!!";
    @Value("${link-shortener.link-length}")
    private Integer linkLength;
    @MockBean
    private LinkInfoRepository linkInfoRepository;
    @Autowired
    private LinkInfoService linkInfoService;

    @Test
    void when_createShortLinkHappy() {
        LinkInfo linkInfo = getLinkInfo().get();
        Mockito.when(linkInfoRepository.save(Mockito.any(LinkInfo.class))).thenReturn(linkInfo);

        LinkInfoResponse result = linkInfoService.createShortLink(getCreateLinkInfoRequest());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(linkLength, result.getShortLink().length());
        Assertions.assertEquals(getLinkInfoResponse().getLink(), result.getLink());
    }

    @Test
    void when_findByShortLinkHappy() {
        Mockito.when(linkInfoRepository.findByShortLink(Mockito.any(String.class))).thenReturn(getLinkInfo());
        LinkInfoResponse result = linkInfoService.findByShortLink(getLinkInfoResponse().getShortLink());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(getLinkInfoResponse().getLink(), result.getLink());
        Assertions.assertEquals(getLinkInfoResponse().getShortLink(), result.getShortLink());
    }

    @Test
    void when_findByShortLinkNotFound() {
        Mockito.when(linkInfoRepository.findByShortLink("nonExistentShortLink")).thenReturn(Optional.empty());

        NotFoundException exception = Assertions.assertThrows(
                NotFoundException.class,
                () -> linkInfoService.findByShortLink("nonExistentShortLink")
        );
        Assertions.assertEquals("Not found LinkInfo by shortLink: nonExistentShortLink", exception.getMessage());
    }



    LocalDateTime getEndTime() {
        return LocalDateTime.now().plusYears(1);
    }

    Optional<LinkInfo> getLinkInfo() {
        return Optional.of(LinkInfo.builder()
                .shortLink("shortlk")
                .link(FULL_LINK)
                .endTime(getEndTime())
                .description("This is link!!!")
                .active(true)
                .build());
    }

    CreateLinkInfoRequest getCreateLinkInfoRequest() {
        return CreateLinkInfoRequest.builder()
                .link(FULL_LINK)
                .active(true)
                .endTime(getEndTime())
                .description("This is link!!!")
                .build();
    }

    LinkInfoResponse getLinkInfoResponse() {
        return LinkInfoResponse.builder()
                .shortLink("shortlk")
                .link(FULL_LINK)
                .endTime(getEndTime())
                .description("This is link!!!")
                .active(true)
                .build();
    }

}
