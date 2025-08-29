package ru.practicum.events;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EventDto;
import ru.practicum.mapper.EventMapper;
import ru.practicum.service.EventService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
@Validated
public class AdminEventController {
    private final EventService service;
    private final EventMapper mapper;

    @PatchMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventDto updateEvent(@PathVariable Long eventId, @Valid @RequestBody EventDto dto) {
        // Предполагается, что метод обновляет статус события или другие поля
        return mapper.toDto(service.updateAdminEvent(eventId, dto));
    }
}