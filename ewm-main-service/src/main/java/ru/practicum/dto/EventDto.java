package ru.practicum.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventDto {
    private Long id;
    private String title;
    private String description;
    private Long categoryId;
    private Long initiatorId;
    private LocalDateTime eventDate;
    private String status;
    private boolean isPaid;
    private int participantLimit;
}
