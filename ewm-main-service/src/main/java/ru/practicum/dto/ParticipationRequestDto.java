package ru.practicum.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipationRequestDto {

    private Long id;
    private Long eventId;
    private Long requesterId;
    private String status;
    private LocalDateTime created; // <-- добавлено
}
