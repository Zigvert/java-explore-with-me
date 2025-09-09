package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.ParticipationRequestDto;
import ru.practicum.mapper.ParticipationRequestMapper;
import ru.practicum.model.ParticipationRequest;
import ru.practicum.service.ParticipationRequestService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ParticipationRequestController {

    private final ParticipationRequestService service;

    // Создание заявки на участие
    @PostMapping("/users/{userId}/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto create(@PathVariable Long userId,
                                          @RequestParam Long eventId) {
        ParticipationRequest request = service.createRequest(userId, eventId);
        return ParticipationRequestMapper.toDto(request);
    }

    // Получение всех заявок пользователя
    @GetMapping("/users/{userId}/requests")
    public List<ParticipationRequestDto> getUserRequests(@PathVariable Long userId) {
        List<ParticipationRequest> requests = service.getUserRequests(userId);
        return requests.stream()
                .map(ParticipationRequestMapper::toDto)
                .collect(Collectors.toList());
    }

    // Отмена заявки пользователем
    @PatchMapping("/users/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto cancel(@PathVariable Long userId,
                                          @PathVariable Long requestId) {
        ParticipationRequest request = service.cancelRequest(userId, requestId);
        return ParticipationRequestMapper.toDto(request);
    }

    // Подтверждение заявки (для администратора/инициатора события)
    @PatchMapping("/events/{eventId}/requests/{requestId}/confirm")
    public ParticipationRequestDto confirm(@PathVariable Long eventId,
                                           @PathVariable Long requestId) {
        ParticipationRequest request = service.confirmRequest(eventId, requestId);
        return ParticipationRequestMapper.toDto(request);
    }

    // Отклонение заявки (для администратора/инициатора события)
    @PatchMapping("/events/{eventId}/requests/{requestId}/reject")
    public ParticipationRequestDto reject(@PathVariable Long eventId,
                                          @PathVariable Long requestId) {
        ParticipationRequest request = service.rejectRequest(eventId, requestId);
        return ParticipationRequestMapper.toDto(request);
    }
}
