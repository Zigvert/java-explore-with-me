package ru.practicum.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewCompilationDto {

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @Builder.Default
    private Boolean pinned = false;

    @Builder.Default
    private List<Long> events = new ArrayList<>(); // ✅ вместо List.of()
}
