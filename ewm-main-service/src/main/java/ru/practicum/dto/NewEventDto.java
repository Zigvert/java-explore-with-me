package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewEventDto {

    @NotBlank(message = "Title cannot be blank")
    @Size(min = 3, max = 120, message = "Title must be between 3 and 120 characters")
    private String title;

    @NotBlank(message = "Description cannot be blank")
    @Size(min = 20, max = 7000, message = "Description must be between 20 and 7000 characters")
    private String description;

    @NotBlank(message = "Annotation cannot be blank")
    @Size(min = 20, max = 2000, message = "Annotation must be between 20 and 2000 characters")
    private String annotation;

    // ⚠️ ВАЖНО: именно category (а не categoryId), чтобы тесты подхватили
    @NotNull(message = "Category ID cannot be null")
    private Long category;

    @NotNull(message = "Event date cannot be null")
    @Future(message = "Event date must be in the future, at least 2 hours from now")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    private Boolean paid = false;

    @PositiveOrZero(message = "Participant limit cannot be negative")
    private Integer participantLimit = 0;

    private Boolean requestModeration = true;

    private LocationDto location;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LocationDto {
        @NotNull(message = "Latitude cannot be null")
        private Float lat;

        @NotNull(message = "Longitude cannot be null")
        private Float lon;
    }
}
