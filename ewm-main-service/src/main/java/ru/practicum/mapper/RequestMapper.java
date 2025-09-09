package ru.practicum.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.dto.RequestDto;
import ru.practicum.model.ParticipationRequest;

@Component
public class RequestMapper {
    public RequestDto toDto(ParticipationRequest request) {
        return RequestDto.builder()
                .id(request.getId())
                .event(request.getEvent().getId())
                .requester(request.getRequester().getId())
                .status(request.getStatus().name())
                .created(request.getCreated())
                .build();
    }
}