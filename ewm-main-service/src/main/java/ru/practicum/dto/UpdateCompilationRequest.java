package ru.practicum.dto;

import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCompilationRequest {

    private String title;
    private Boolean pinned;

    @Builder.Default
    private List<Long> events = new ArrayList<>(); // ✅ всегда список
}
