package ru.practicum.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.dto.EventDto;
import ru.practicum.model.Category;
import ru.practicum.model.Event;
import ru.practicum.model.User;

@Component
public class EventMapper {

    public EventDto toDto(Event event) {
        return EventDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .categoryId(event.getCategory() != null ? event.getCategory().getId() : null)
                .initiatorId(event.getInitiator() != null ? event.getInitiator().getId() : null)
                .eventDate(event.getEventDate())
                .status(event.getStatus() != null ? event.getStatus().name() : null)
                .isPaid(event.isPaid())
                .participantLimit(event.getParticipantLimit())
                .build();
    }

    public Event toEntity(EventDto dto, Category category, User initiator) {
        return Event.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .category(category)
                .initiator(initiator)
                .eventDate(dto.getEventDate())
                .status(dto.getStatus() != null ? ru.practicum.model.EventStatus.valueOf(dto.getStatus()) : null)
                .isPaid(dto.isPaid())
                .participantLimit(dto.getParticipantLimit())
                .build();
    }
}
