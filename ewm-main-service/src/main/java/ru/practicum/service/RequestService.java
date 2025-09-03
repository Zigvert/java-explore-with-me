package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;
import ru.practicum.dto.RequestDto;
import ru.practicum.mapper.RequestMapper;
import ru.practicum.model.Event;
import ru.practicum.model.ParticipationRequest;
import ru.practicum.model.User;
import ru.practicum.repository.EventRepository;
import ru.practicum.repository.RequestRepository;
import ru.practicum.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final RequestMapper requestMapper;

    @Transactional
    public RequestDto create(Long userId, Long eventId) {
        User requester = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found: " + eventId));

        if (event.getInitiator().getId().equals(userId)) {
            throw new IllegalArgumentException("Initiator cannot request participation in own event");
        }
        if (event.getStatus() != EventStatus.PUBLISHED) {
            throw new IllegalArgumentException("Event is not published: " + eventId);
        }
        if (event.getParticipantLimit() > 0) {
            long confirmedRequests = requestRepository.countByEventIdAndStatus(eventId, ParticipationRequest.RequestStatus.CONFIRMED);
            if (confirmedRequests >= event.getParticipantLimit()) {
                throw new IllegalArgumentException("Participant limit reached for event: " + eventId);
            }
        }

        ParticipationRequest request = ParticipationRequest.builder()
                .event(event)
                .requester(requester)
                .created(LocalDateTime.now())
                .status(event.getParticipantLimit() == 0 || !event.isRequestModeration() ?
                        ParticipationRequest.RequestStatus.CONFIRMED :
                        ParticipationRequest.RequestStatus.PENDING)
                .build();

        if (request.getStatus() == ParticipationRequest.RequestStatus.CONFIRMED) {
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            eventRepository.save(event);
        }

        return requestMapper.toDto(requestRepository.save(request));
    }

    @Transactional(readOnly = true)
    public List<RequestDto> getUserRequests(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
        return requestRepository.findByRequesterId(userId).stream()
                .map(requestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public RequestDto cancel(Long userId, Long requestId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
        ParticipationRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("Request not found: " + requestId));

        if (!request.getRequester().getId().equals(userId)) {
            throw new IllegalArgumentException("User is not the requester: " + requestId);
        }

        request.setStatus(ParticipationRequest.RequestStatus.CANCELED);
        if (request.getStatus() == ParticipationRequest.RequestStatus.CONFIRMED) {
            Event event = request.getEvent();
            event.setConfirmedRequests(event.getConfirmedRequests() - 1);
            eventRepository.save(event);
        }

        return requestMapper.toDto(requestRepository.save(request));
    }
}