package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.model.EndpointHit;
import ru.practicum.repository.EndpointHitRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsService {

    private final EndpointHitRepository repository;

    public void saveHit(EndpointHitDto dto) {
        EndpointHit hit = EndpointHit.builder()
                .app(dto.getApp())
                .uri(dto.getUri())
                .ip(dto.getIp())
                .timestamp(dto.getTimestamp())
                .build();
        repository.save(hit);
    }

    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        boolean hasUris = uris != null && !uris.isEmpty();

        if (unique) {
            if (hasUris) {
                return repository.findStatsUniqueByUris(start, end, uris);
            } else {
                return repository.findStatsUnique(start, end);
            }
        } else {
            if (hasUris) {
                return repository.findStatsByUris(start, end, uris);
            } else {
                return repository.findStats(start, end);
            }
        }
    }
}
