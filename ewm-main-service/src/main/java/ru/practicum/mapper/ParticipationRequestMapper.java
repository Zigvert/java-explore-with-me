package ru.practicum.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.ParticipationRequestDto;
import ru.practicum.model.ParticipationRequest;

@UtilityClass
public class ParticipationRequestMapper {

    public ParticipationRequestDto toDto(ParticipationRequest request) {
        if (request == null) return null;

        return ParticipationRequestDto.builder()
                .id(request.getId())
                .eventId(request.getEvent() != null ? request.getEvent().getId() : null)
                .requesterId(request.getRequester() != null ? request.getRequester().getId() : null)
                .status(request.getStatus() != null ? request.getStatus().name() : null)
                .created(request.getCreated())
                .build();
    }
}
