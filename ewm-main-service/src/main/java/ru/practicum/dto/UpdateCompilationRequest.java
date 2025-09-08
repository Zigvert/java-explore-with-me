package ru.practicum.dto;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCompilationRequest {
    private String title;
    private Boolean pinned;
    private List<Long> events;
}
