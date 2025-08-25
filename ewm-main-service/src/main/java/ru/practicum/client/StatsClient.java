package ru.practicum.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.practicum.dto.EndpointHitDto;

@Component
@RequiredArgsConstructor
public class StatsClient {

    private final RestTemplate restTemplate;

    private final String statsServiceUrl = "http://localhost:9090"; // URL StatsService

    public void saveHit(EndpointHitDto dto) {
        restTemplate.postForEntity(statsServiceUrl + "/hit", dto, Void.class);
    }
}
