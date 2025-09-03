package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;
import ru.practicum.dto.EventDto;
import ru.practicum.mapper.EventMapper;
import ru.practicum.model.Category;
import ru.practicum.model.Event;
import ru.practicum.model.EventStatus;
import ru.practicum.model.User;
import ru.practicum.repository.CategoryRepository;
import ru.practicum.repository.EventRepository;
import ru.practicum.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final EventMapper eventMapper;

    @Transactional
    public Event create(EventDto dto, Long userId, Long categoryId) {
        User initiator = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found: " + categoryId));

        Event event = eventMapper.toEntityForCreate(dto);
        event.setInitiator(initiator);
        event.setCategory(category);
        event.setViews(0L);
        event.setConfirmedRequests(0);
        event.setCreatedAt(LocalDateTime.now());
        event.setStatus(EventStatus.PENDING);

        validateEvent(event);
        return eventRepository.save(event);
    }

    @Transactional(readOnly = true)
    public List<Event> getAll(String text,
                              List<Long> categoryIds,
                              Boolean paid,
                              LocalDateTime rangeStart,
                              LocalDateTime rangeEnd,
                              Boolean onlyAvailable,
                              String sort,
                              int from,
                              int size) {
        int page = from / size;
        Sort sortOption;

        if ("EVENT_DATE".equalsIgnoreCase(sort)) {
            sortOption = Sort.by("eventDate").ascending();
        } else if ("VIEWS".equalsIgnoreCase(sort)) {
            sortOption = Sort.by("views").descending();
        } else {
            sortOption = Sort.unsorted();
        }

        Pageable pageable = PageRequest.of(page, size, sortOption);
        LocalDateTime start = rangeStart != null ? rangeStart : LocalDateTime.now();
        LocalDateTime end = rangeEnd != null ? rangeEnd : LocalDateTime.now().plusYears(50);

        return eventRepository.findEvents(
                EventStatus.PUBLISHED,
                text != null ? text.toLowerCase() : null,
                categoryIds,
                paid,
                start,
                end,
                Boolean.TRUE.equals(onlyAvailable),
                pageable
        );
    }

    @Transactional
    public Event getById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event not found: " + id));

        if (event.getStatus() != EventStatus.PUBLISHED) {
            throw new EntityNotFoundException("Event is not published: " + id);
        }

        event.setViews(event.getViews() + 1);
        return eventRepository.save(event);
    }

    @Transactional(readOnly = true)
    public List<Event> getUserEvents(Long userId, int from, int size) {
        userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));

        int page = from / size;
        Pageable pageable = PageRequest.of(page, size, Sort.by("eventDate").ascending());

        return eventRepository.findByInitiatorId(userId, pageable);
    }

    @Transactional
    public Event updateUserEvent(Long userId, Long eventId, EventDto dto) {
        userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));

        Event existingEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found: " + eventId));

        if (!existingEvent.getInitiator().getId().equals(userId)) {
            throw new IllegalArgumentException("User is not the initiator of the event: " + eventId);
        }
        if (existingEvent.getStatus() == EventStatus.PUBLISHED) {
            throw new IllegalArgumentException("Cannot update published event: " + eventId);
        }

        eventMapper.updateEntityFromDto(dto, existingEvent);

        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found: " + dto.getCategoryId()));
            existingEvent.setCategory(category);
        }

        validateEvent(existingEvent);
        return eventRepository.save(existingEvent);
    }

    @Transactional
    public Event updateAdminEvent(Long eventId, EventDto dto) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found: " + eventId));

        eventMapper.updateEntityFromDto(dto, event);

        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found: " + dto.getCategoryId()));
            event.setCategory(category);
        }

        validateEvent(event);

        if (dto.getStatus() != null) {
            if (EventStatus.PUBLISHED.name().equals(dto.getStatus())) {
                if (event.getEventDate().isBefore(LocalDateTime.now().plusHours(1))) {
                    throw new IllegalArgumentException("Published event date must be at least 1 hour in the future");
                }
                if (event.getPublishedAt() == null) {
                    event.setPublishedAt(LocalDateTime.now());
                }
                event.setStatus(EventStatus.PUBLISHED);
            } else if (EventStatus.CANCELED.name().equals(dto.getStatus())) {
                event.setStatus(EventStatus.CANCELED);
            }
        }

        return eventRepository.save(event);
    }

    private void validateEvent(Event event) {
        if (event.getEventDate() != null &&
                event.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
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
        if (event.getTitle() == null || event.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
    }
}
