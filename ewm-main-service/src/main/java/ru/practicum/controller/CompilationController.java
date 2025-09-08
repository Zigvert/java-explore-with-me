package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.CompilationDto;
import ru.practicum.dto.NewCompilationDto;
import ru.practicum.dto.UpdateCompilationRequest;
import ru.practicum.service.CompilationService;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class CompilationController {

    private final CompilationService compilationService;

    // ----------------------------
    // Получение всех подборок
    // ----------------------------
    @GetMapping("/compilations")
    public List<CompilationDto> getAll(
            @RequestParam(required = false) Boolean pinned,
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "10") int size) {
        return compilationService.getAll(pinned, from, size);
    }

    // ----------------------------
    // Создание подборки (админ)
    // ----------------------------
    @PostMapping("/admin/compilations")
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto create(@Valid @RequestBody NewCompilationDto dto) {
        return compilationService.create(dto);
    }

    // ----------------------------
    // Обновление подборки (админ)
    // ----------------------------
    @PatchMapping("/admin/compilations/{compId}")
    public CompilationDto update(
            @PathVariable Long compId,
            @RequestBody UpdateCompilationRequest request) {
        return compilationService.update(compId, request);
    }

    // ----------------------------
    // Удаление подборки (админ)
    // ----------------------------
    @DeleteMapping("/admin/compilations/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long compId) {
        compilationService.delete(compId);
    }
}
