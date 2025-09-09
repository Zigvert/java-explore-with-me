package ru.practicum.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipationRequestDto {
    private Long id;
    private LocalDateTime created;
    private String status; // PENDING, CONFIRMED, REJECTED, CANCELED
    private Long eventId;
    private Long requesterId;
}
