package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EventDto;
import ru.practicum.dto.NewEventDto;
import ru.practicum.mapper.EventMapper;
import ru.practicum.model.Event;
import ru.practicum.service.EventService;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final EventMapper eventMapper;

    @PostMapping
    public ResponseEntity<EventDto> createEvent(@Valid @RequestBody NewEventDto dto,
                                                @RequestParam Long userId) {
        Event event = eventService.create(dto, userId);
        return new ResponseEntity<>(eventMapper.toDto(event), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EventDto>> getAllEvents(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false) String rangeStart,
            @RequestParam(required = false) String rangeEnd,
            @RequestParam(defaultValue = "false") Boolean onlyAvailable,
            @RequestParam(defaultValue = "EVENT_DATE") String sort,
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "10") int size) {

        // Для простоты оставляем rangeStart и rangeEnd как null, парсинг можно добавить
        List<Event> events = eventService.getAll(
                text, categories, paid, null, null, onlyAvailable, sort, from, size
        );
        return ResponseEntity.ok(events.stream().map(eventMapper::toDto).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getEventById(@PathVariable Long id) {
        Event event = eventService.getById(id);
        return ResponseEntity.ok(eventMapper.toDto(event));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EventDto> updateUserEvent(@RequestParam Long userId,
                                                    @PathVariable Long id,
                                                    @RequestBody EventDto dto) {
        Event updated = eventService.updateUserEvent(userId, id, dto);
        return ResponseEntity.ok(eventMapper.toDto(updated));
    }

    @PatchMapping("/admin/{id}")
    public ResponseEntity<EventDto> updateAdminEvent(@PathVariable Long id,
                                                     @RequestBody EventDto dto) {
        Event updated = eventService.updateAdminEvent(id, dto);
        return ResponseEntity.ok(eventMapper.toDto(updated));
    }
}
