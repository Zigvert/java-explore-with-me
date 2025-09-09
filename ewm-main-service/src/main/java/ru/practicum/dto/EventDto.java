package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventDto {
    private Long id;

    @NotBlank(message = "Title cannot be blank")
    @Size(min = 3, max = 120)
    private String title;

    @NotBlank(message = "Description cannot be blank")
    @Size(min = 20, max = 7000)
    private String description;

    @NotBlank(message = "Annotation cannot be blank")
    @Size(min = 20, max = 2000)
    private String annotation;

    private Long categoryId;
    private Long initiatorId;

    @NotNull(message = "Event date cannot be null")
    @Future(message = "Event date must be in the future, at least 2 hours from now")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    private String status;
    private Boolean paid;

    @PositiveOrZero(message = "Participant limit cannot be negative")
    private Integer participantLimit;

    private Boolean requestModeration;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedOn;

    private Long views;
    private Integer confirmedRequests;

    private LocationDto location;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LocationDto {
        @NotNull
        private Float lat;
        @NotNull
        private Float lon;
    }
}
