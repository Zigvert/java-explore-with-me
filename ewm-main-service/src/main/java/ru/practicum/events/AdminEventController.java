package ru.practicum.events;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EventDto;
import ru.practicum.mapper.EventMapper;
import ru.practicum.service.EventService;

@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
public class AdminEventController {
    private final EventService service;
    private final EventMapper mapper;

    @PatchMapping("/{eventId}")
    public EventDto updateStatus(@PathVariable Long eventId, @RequestBody EventDto dto) {
        // Implement publish/reject logic
        throw new UnsupportedOperationException("Not implemented");
    }
}