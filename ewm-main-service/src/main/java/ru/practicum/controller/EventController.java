package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.client.StatsClient;
import ru.practicum.dto.EventDto;
import ru.practicum.mapper.EventMapper;
import ru.practicum.model.Event;
import ru.practicum.service.EventService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService service;
    private final EventMapper mapper;
    private final StatsClient statsClient;

    @PostMapping
    public EventDto create(@RequestBody EventDto dto) {
        Event event = mapper.toEntity(dto, null, null); // категорию и инициатора добавим позже
        Event created = service.create(event);

        // отправка данных о просмотре для StatsService
        statsClient.saveHit(new ru.practicum.dto.EndpointHitDto(
                "main-service", "/events", "127.0.0.1", LocalDateTime.now()
        ));

        return mapper.toDto(created);
    }

    @GetMapping
    public List<EventDto> getAll() {
        return service.getAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public EventDto getById(@PathVariable Long id) {
        Event event = service.getById(id);

        // отправка данных о просмотре для StatsService
        statsClient.saveHit(new ru.practicum.dto.EndpointHitDto(
                "main-service", "/events/" + id, "127.0.0.1", LocalDateTime.now()
        ));

        return mapper.toDto(event);
    }
}
