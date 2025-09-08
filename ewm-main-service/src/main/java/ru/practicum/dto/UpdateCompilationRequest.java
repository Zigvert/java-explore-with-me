package ru.practicum.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCompilationRequest {
    private String title;             // 🔹 может быть null → обновляем только если пришло
    private Boolean pinned;           // 🔹 можно менять только pinned
    private List<Long> events;        // 🔹 список новых событий
}
