package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.EventDto;
import ru.practicum.mapper.EventMapper;
import ru.practicum.model.Event;
import ru.practicum.service.EventService;
import ru.practicum.client.StatsClient;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Validated
public class EventController {
    private final EventService service;
    private final EventMapper mapper;
    private final StatsClient statsClient;

    // ----------- Public endpoints ------------
    @GetMapping("/events")
    public List<EventDto> getAll(@RequestParam(required = false) String text,
                                 @RequestParam(required = false) List<Long> categories,
                                 @RequestParam(required = false) Boolean paid,
                                 @RequestParam(required = false) LocalDateTime rangeStart,
                                 @RequestParam(required = false) LocalDateTime rangeEnd,
                                 @RequestParam(defaultValue = "false") boolean onlyAvailable,
                                 @RequestParam(defaultValue = "EVENT_DATE") String sort,
                                 @RequestParam(defaultValue = "0") int from,
                                 @RequestParam(defaultValue = "10") int size) {
        List<Event> events = service.getAll(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
        statsClient.saveHit(new EndpointHitDto("main-service", "/events", "127.0.0.1", LocalDateTime.now()));
        return events.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("/events/{id}")
    public EventDto getById(@PathVariable Long id) {
        Event event = service.getById(id);
        statsClient.saveHit(new EndpointHitDto("main-service", "/events/" + id, "127.0.0.1", LocalDateTime.now()));
        return mapper.toDto(event);
    }

    // ----------- Private endpoints ------------
    @PostMapping("/users/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventDto create(@PathVariable Long userId, @Valid @RequestBody EventDto dto) {
        Event created = service.create(dto, userId, dto.getCategoryId());
        return mapper.toDto(created);
    }

    @GetMapping("/users/{userId}/events")
    public List<EventDto> getUserEvents(@PathVariable Long userId,
                                        @RequestParam(defaultValue = "0") int from,
                                        @RequestParam(defaultValue = "10") int size) {
        List<Event> events = service.getUserEvents(userId, from, size);
        return events.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @PatchMapping("/users/{userId}/events/{eventId}")
    public EventDto updateUserEvent(@PathVariable Long userId,
                                    @PathVariable Long eventId,
                                    @Valid @RequestBody EventDto dto) {
        Event updated = service.updateUserEvent(userId, eventId, dto);
        return mapper.toDto(updated);
    }
}
