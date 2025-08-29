package ru.practicum.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.dto.EventDto;
import ru.practicum.model.Category;
import ru.practicum.model.Event;
import ru.practicum.model.EventStatus;
import ru.practicum.model.User;

@Component
public class EventMapper {

    public EventDto toDto(Event event) {
        EventDto dto = new EventDto();
        dto.setId(event.getId());
        dto.setTitle(event.getTitle());
        dto.setAnnotation(event.getAnnotation());
        dto.setDescription(event.getDescription());
        dto.setCategoryId(event.getCategory() != null ? event.getCategory().getId() : null);
        dto.setInitiatorId(event.getInitiator() != null ? event.getInitiator().getId() : null);
        dto.setEventDate(event.getEventDate());
        dto.setStatus(event.getStatus() != null ? event.getStatus().name() : null);
        dto.setPaid(event.isPaid());
        dto.setParticipantLimit(event.getParticipantLimit());
        dto.setRequestModeration(event.isRequestModeration());
        dto.setCreatedOn(event.getCreatedAt());
        dto.setPublishedOn(event.getPublishedAt());
        if (event.getLocation() != null) {
            dto.setLocation(new EventDto.LocationDto(event.getLocation().getLat(), event.getLocation().getLon()));
        }
        return dto;
    }

    /** Для создания нового события (User/Category подтянет сервис) */
    public Event toEntityForCreate(EventDto dto) {
        Event event = new Event();
        event.setTitle(dto.getTitle());
        event.setAnnotation(dto.getAnnotation());
        event.setDescription(dto.getDescription());
        event.setEventDate(dto.getEventDate());
        event.setPaid(dto.isPaid());
        event.setParticipantLimit(dto.getParticipantLimit());
        event.setRequestModeration(dto.isRequestModeration());
        if (dto.getLocation() != null) {
            event.setLocation(new Event.Location(dto.getLocation().getLat(), dto.getLocation().getLon()));
        }
        return event;
    }

    /** Для обновления события */
    public void updateEntityFromDto(EventDto dto, Event event) {
        if (dto.getTitle() != null) event.setTitle(dto.getTitle());
        if (dto.getAnnotation() != null) event.setAnnotation(dto.getAnnotation());
        if (dto.getDescription() != null) event.setDescription(dto.getDescription());
        if (dto.getEventDate() != null) event.setEventDate(dto.getEventDate());
        if (dto.getStatus() != null) {
            try {
                event.setStatus(EventStatus.valueOf(dto.getStatus()));
            } catch (IllegalArgumentException ignored) {}
        }
        event.setPaid(dto.isPaid());
        event.setParticipantLimit(dto.getParticipantLimit());
        event.setRequestModeration(dto.isRequestModeration());
        if (dto.getLocation() != null) {
            event.setLocation(new Event.Location(dto.getLocation().getLat(), dto.getLocation().getLon()));
        }
    }
}
