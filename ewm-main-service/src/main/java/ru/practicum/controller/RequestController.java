package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.RequestDto;
import ru.practicum.service.RequestService;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/event-requests")  // <- изменено, чтобы не пересекалось
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;

    // Создание нового запроса на участие в событии
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RequestDto create(@PathVariable Long userId,
                             @RequestParam Long eventId) {
        return requestService.create(userId, eventId);
    }

    // Получение всех запросов пользователя
    @GetMapping
    public List<RequestDto> getUserRequests(@PathVariable Long userId) {
        return requestService.getUserRequests(userId);
    }

    // Отмена конкретного запроса пользователя
    @PatchMapping("/{requestId}/cancel")
    public RequestDto cancel(@PathVariable Long userId,
                             @PathVariable Long requestId) {
        return requestService.cancel(userId, requestId);
    }
}
