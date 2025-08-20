package ru.practicum.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class StatsClient {

    private final RestTemplate restTemplate;

    @Value("${stats.service.url}")
    private String statsServiceUrl;

    public void saveHit(EndpointHitDto hitDto) {
        String url = statsServiceUrl + "/hit";
        try {
            restTemplate.postForEntity(url, hitDto, Void.class);
            log.info("Событие сохранено в сервис статистики: {}", hitDto);
        } catch (Exception e) {
            log.error("Ошибка при сохранении события в сервис статистики: {}", e.getMessage(), e);
        }
    }

    public List<ViewStatsDto> getStats(String start, String end, List<String> uris, boolean unique) {
        try {
            String startEncoded = URLEncoder.encode(start, StandardCharsets.UTF_8);
            String endEncoded = URLEncoder.encode(end, StandardCharsets.UTF_8);

            StringBuilder urlBuilder = new StringBuilder(statsServiceUrl)
                    .append("/stats?start=").append(startEncoded)
                    .append("&end=").append(endEncoded);

            if (uris != null && !uris.isEmpty()) {
                String joinedUris = String.join(",", uris);
                urlBuilder.append("&uris=").append(URLEncoder.encode(joinedUris, StandardCharsets.UTF_8));
            }

            urlBuilder.append("&unique=").append(unique);

            String finalUrl = urlBuilder.toString();
            log.debug("Отправляем запрос в сервис статистики: {}", finalUrl);

            ResponseEntity<ViewStatsDto[]> response =
                    restTemplate.getForEntity(finalUrl, ViewStatsDto[].class);

            ViewStatsDto[] body = response.getBody();
            return body != null ? Arrays.asList(body) : Collections.emptyList();

        } catch (Exception e) {
            log.error("Ошибка при получении статистики: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }
}
