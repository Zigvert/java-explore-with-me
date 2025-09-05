package ru.practicum.dto;

import lombok.*;
import ru.practicum.dto.EventDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompilationDto {
    private Long id;
    private String title;
    private boolean pinned;
    private List<EventDto> events;
}
