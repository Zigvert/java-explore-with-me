package ru.practicum.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestDto {
    private Long id;
    private Long event;
    private Long requester;
    private String status;
    private LocalDateTime created;
}