package ru.practicum.client;

import org.springframework.http.*;
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
    private final String statsServiceUrl = "http://localhost:9090"; // можно параметризовать

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

            StringBuilder urlBuilder = new StringBuilder(statsServiceUrl + "/stats?start=" + startEncoded + "&end=" + endEncoded);
            if (uris != null && !uris.isEmpty()) {
                for (String uri : uris) {
                    urlBuilder.append("&uris=").append(URLEncoder.encode(uri, StandardCharsets.UTF_8.toString()));
                }
            }
            urlBuilder.append("&unique=").append(unique);

            ResponseEntity<ViewStatsDto[]> response = restTemplate.getForEntity(urlBuilder.toString(), ViewStatsDto[].class);
            return Arrays.asList(Objects.requireNonNull(response.getBody()));
        } catch (Exception e) {
            // обработка ошибок
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
