package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.model.Category;
import ru.practicum.model.Event;
import ru.practicum.model.EventStatus;
import ru.practicum.model.User;
import ru.practicum.repository.CategoryRepository;
import ru.practicum.repository.EventRepository;
import ru.practicum.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EventService {

    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Transactional
    public Event create(Event event, Long userId, Long categoryId) {
        validateEvent(event);
        User initiator = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found: " + categoryId));
        event.setInitiator(initiator);
        event.setCategory(category);
        event.setCreatedAt(LocalDateTime.now());
        event.setStatus(EventStatus.PENDING);
        return eventRepository.save(event);
    }

    @Transactional(readOnly = true)
    public List<Event> getAll(String text, List<Long> categoryIds, Boolean paid, LocalDateTime rangeStart,
                              LocalDateTime rangeEnd, Boolean onlyAvailable, String sort, int from, int size) {
        LocalDateTime start = rangeStart != null ? rangeStart : LocalDateTime.now();
        LocalDateTime end = rangeEnd != null ? rangeEnd : LocalDateTime.now().plusYears(100);
        return eventRepository.findEvents(text, categoryIds, paid, start, end, onlyAvailable, sort, from, size);
    }

    @Transactional(readOnly = true)
    public Event getById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event not found: " + id));
        if (event.getStatus() != EventStatus.PUBLISHED) {
            throw new EntityNotFoundException("Event is not published: " + id);
        }
        return event;
    }

    private void validateEvent(Event event) {
        if (event.getEventDate() != null && event.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new IllegalArgumentException("Event date must be at least 2 hours in the future");
        }
        if (event.getParticipantLimit() < 0) {
            throw new IllegalArgumentException("Participant limit cannot be negative");
        }
        if (event.getAnnotation() == null || event.getAnnotation().trim().isEmpty()) {
            throw new IllegalArgumentException("Annotation cannot be empty");
        }
        if (event.getDescription() == null || event.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }
    }
}