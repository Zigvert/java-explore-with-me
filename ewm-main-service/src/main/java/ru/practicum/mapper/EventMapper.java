package ru.practicum.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.dto.EventDto;
import ru.practicum.model.Category;
import ru.practicum.model.Event;
import ru.practicum.model.EventStatus;
import ru.practicum.model.User;

import java.time.LocalDateTime;

@Component
public class EventMapper {

    public EventDto toDto(Event event) {
        return EventDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .annotation(event.getAnnotation())
                .categoryId(event.getCategory() != null ? event.getCategory().getId() : null)
                .initiatorId(event.getInitiator() != null ? event.getInitiator().getId() : null)
                .eventDate(event.getEventDate())
                .status(event.getStatus() != null ? event.getStatus().name() : null)
                .isPaid(event.isPaid())
                .participantLimit(event.getParticipantLimit())
                .requestModeration(event.isRequestModeration())
                .createdOn(event.getCreatedAt())
                .publishedOn(event.getPublishedAt())
                .location(event.getLocation() != null ?
                        EventDto.LocationDto.builder()
                                .lat(event.getLocation().getLat())
                                .lon(event.getLocation().getLon())
                                .build() : null)
                .build();
    }

    public Event toEntity(EventDto dto, Category category, User initiator) {
        return Event.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .annotation(dto.getAnnotation())
                .category(category)
                .initiator(initiator)
                .eventDate(dto.getEventDate())
                .status(dto.getStatus() != null ? EventStatus.valueOf(dto.getStatus()) : EventStatus.PENDING)
                .isPaid(dto.isPaid())
                .participantLimit(dto.getParticipantLimit())
                .requestModeration(dto.isRequestModeration())
                .createdAt(dto.getCreatedOn() != null ? dto.getCreatedOn() : LocalDateTime.now())
                .publishedAt(dto.getPublishedOn())
                .location(dto.getLocation() != null ?
                        new Event.Location(dto.getLocation().getLat(), dto.getLocation().getLon()) : null)
                .build();
    }
}