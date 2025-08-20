package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.model.Event;
import ru.practicum.model.EventStatus;
import ru.practicum.repository.EventRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository repository;

    public Event create(Event event) {
        event.setCreatedAt(LocalDateTime.now());
        event.setStatus(EventStatus.PENDING);
        return repository.save(event);
    }

    public List<Event> getAll() {
        return repository.findAll();
    }

    public Event getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Event not found"));
    }
}
