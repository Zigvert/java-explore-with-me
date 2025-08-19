package ru.practicum.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public class StatsClient {

    private final RestTemplate restTemplate;

    @Value("${stats.service.url}")
    private String statsServiceUrl; // подтягиваем из application.properties

    public StatsClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void saveHit(EndpointHitDto hitDto) {
        String url = statsServiceUrl + "/hit";
        restTemplate.postForEntity(url, hitDto, Void.class);
    }

    public List<ViewStatsDto> getStats(String start, String end, List<String> uris, boolean unique) {
        try {
            String startEncoded = URLEncoder.encode(start, StandardCharsets.UTF_8.toString());
            String endEncoded = URLEncoder.encode(end, StandardCharsets.UTF_8.toString());

            StringBuilder urlBuilder = new StringBuilder(statsServiceUrl)
                    .append("/stats?start=").append(startEncoded)
                    .append("&end=").append(endEncoded);

            if (uris != null && !uris.isEmpty()) {
                String joinedUris = String.join(",", uris);
                urlBuilder.append("&uris=").append(URLEncoder.encode(joinedUris, StandardCharsets.UTF_8.toString()));
            }

            urlBuilder.append("&unique=").append(unique);

            ResponseEntity<ViewStatsDto[]> response =
                    restTemplate.getForEntity(urlBuilder.toString(), ViewStatsDto[].class);

            return Arrays.asList(Objects.requireNonNull(response.getBody()));
        } catch (Exception e) {
            // логируем и возвращаем пустой список
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
