package ru.practicum.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title cannot be blank")
    @Size(min = 3, max = 120)
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Annotation cannot be blank")
    @Size(min = 20, max = 2000)
    @Column(nullable = false)
    private String annotation;

    @NotBlank(message = "Description cannot be blank")
    @Size(min = 20, max = 7000)
    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User initiator;

    @NotNull(message = "Event date cannot be null")
    @Column(nullable = false)
    private LocalDateTime eventDate;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime publishedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventStatus status;

    @Column(name = "is_paid")
    private boolean isPaid;

    @PositiveOrZero(message = "Participant limit cannot be negative")
    @Column(name = "participant_limit")
    private int participantLimit;

    @Column(name = "request_moderation")
    private boolean requestModeration;

    @Embedded
    private Location location;

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Location {
        @NotNull
        private Float lat;

        @NotNull
        private Float lon;
    }
}