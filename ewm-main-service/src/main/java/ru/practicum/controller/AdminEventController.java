package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EventDto;
import ru.practicum.mapper.EventMapper;
import ru.practicum.model.Event;
import ru.practicum.service.EventService;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
@Validated
public class AdminEventController {
    private final EventService eventService;
    private final EventMapper eventMapper;

    @GetMapping
    public List<EventDto> getEvents(@RequestParam(required = false) List<Long> users,
                                    @RequestParam(required = false) List<String> states,
                                    @RequestParam(required = false) List<Long> categories,
                                    @RequestParam(required = false) LocalDateTime rangeStart,
                                    @RequestParam(required = false) LocalDateTime rangeEnd,
                                    @RequestParam(defaultValue = "0") int from,
                                    @RequestParam(defaultValue = "10") int size) {
        String text = null; // Можно добавить параметр text, если нужен поиск по тексту
        List<Long> categoryIds = categories;
        Boolean paid = null; // Можно добавить параметр paid, если нужен
        Boolean onlyAvailable = false; // Можно добавить параметр onlyAvailable, если нужен
        String sort = "EVENT_DATE"; // По умолчанию сортировка по дате
        List<Event> events = eventService.getAll(
                text,
                categoryIds,
                paid,
                rangeStart,
                rangeEnd,
                onlyAvailable,
                sort,
                from,
                size
        );
        return events.stream().map(eventMapper::toDto).collect(Collectors.toList());
    }

    @PatchMapping("/{eventId}")
    public EventDto update(@PathVariable Long eventId, @Valid @RequestBody EventDto dto) {
        Event updated = eventService.updateAdminEvent(eventId, dto);
        return eventMapper.toDto(updated);
    }
}