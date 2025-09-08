package ru.practicum.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewCompilationDto {
    @NotBlank(message = "Title cannot be blank")
    private String title;

    private Boolean pinned = false;
    private List<Long> events = List.of(); // дефолт пустой список
}
