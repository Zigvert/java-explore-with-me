package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;
import ru.practicum.model.*;
import ru.practicum.repository.EventRepository;
import ru.practicum.repository.ParticipationRequestRepository;
import ru.practicum.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipationRequestService {

    private final ParticipationRequestRepository requestRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Transactional
    public ParticipationRequest createRequest(Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found: " + eventId));

        if (event.getInitiator().getId().equals(userId)) {
            throw new IllegalArgumentException("Initiator cannot request participation in own event");
        }

        ParticipationRequest request = ParticipationRequest.builder()
                .created(LocalDateTime.now())
                .status(RequestStatus.PENDING)
                .event(event)
                .requester(user)
                .build();

        return requestRepository.save(request);
    }

    @Transactional(readOnly = true)
    public List<ParticipationRequest> getUserRequests(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
        return requestRepository.findByRequesterId(userId);
    }

    @Transactional
    public ParticipationRequest cancelRequest(Long userId, Long requestId) {
        ParticipationRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("Request not found: " + requestId));

        if (!request.getRequester().getId().equals(userId)) {
            throw new IllegalArgumentException("User is not the owner of the request");
        }

        request.setStatus(RequestStatus.CANCELED);
        return requestRepository.save(request);
    }

    @Transactional
    public ParticipationRequest confirmRequest(Long eventId, Long requestId) {
        ParticipationRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("Request not found: " + requestId));

        Event event = request.getEvent();

        if (!event.getId().equals(eventId)) {
            throw new IllegalArgumentException("Request does not belong to the event");
        }

        if (request.getStatus() != RequestStatus.PENDING) {
            throw new IllegalArgumentException("Only pending requests can be confirmed");
        }

        request.setStatus(RequestStatus.CONFIRMED);
        event.setConfirmedRequests(event.getConfirmedRequests() + 1);
        return requestRepository.save(request);
    }

    @Transactional
    public ParticipationRequest rejectRequest(Long eventId, Long requestId) {
        ParticipationRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("Request not found: " + requestId));

        Event event = request.getEvent();

        if (!event.getId().equals(eventId)) {
            throw new IllegalArgumentException("Request does not belong to the event");
        }

        if (request.getStatus() != RequestStatus.PENDING) {
            throw new IllegalArgumentException("Only pending requests can be rejected");
        }

        request.setStatus(RequestStatus.REJECTED);
        return requestRepository.save(request);
    }
}
