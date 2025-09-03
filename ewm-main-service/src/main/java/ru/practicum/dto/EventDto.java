package ru.practicum.dto;

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
    @Size(min = 3, max = 120, message = "Title must be between 3 and 120 characters")
    private String title;

    @NotBlank(message = "Description cannot be blank")
    @Size(min = 20, max = 7000, message = "Description must be between 20 and 7000 characters")
    private String description;

    @NotBlank(message = "Annotation cannot be blank")
    @Size(min = 20, max = 2000, message = "Annotation must be between 20 and 2000 characters")
    private String annotation;

    @NotNull(message = "Category ID cannot be null")
    private Long categoryId;

    private Long initiatorId;

    @NotNull(message = "Event date cannot be null")
    @Future(message = "Event date must be in the future, at least 2 hours from now")
    private LocalDateTime eventDate;

    private String status;

    private Boolean isPaid;

    @PositiveOrZero(message = "Participant limit cannot be negative")
    private Integer participantLimit;

    private Boolean requestModeration;

    private LocalDateTime createdOn;
    private LocalDateTime publishedOn;

    private Long views;
    private Integer confirmedRequests;

    private LocationDto location;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LocationDto {
        @NotNull(message = "Latitude cannot be null")
        private Float lat;

        @NotNull(message = "Longitude cannot be null")
        private Float lon;
    }
}