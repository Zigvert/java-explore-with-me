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
    @Column(nullable = false, length = 120)
    private String title;

    @NotBlank(message = "Annotation cannot be blank")
    @Size(min = 20, max = 2000)
    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String annotation;

    @NotBlank(message = "Description cannot be blank")
    @Size(min = 20, max = 7000)
    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User initiator;

    @NotNull(message = "Event date cannot be null")
    @Column(nullable = false)
    private LocalDateTime eventDate;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime publishedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventStatus status;

    @Column(nullable = false)
    private boolean paid;

    @PositiveOrZero(message = "Participant limit cannot be negative")
    @Column(nullable = false)
    private int participantLimit;

    @Column(name = "request_moderation", nullable = false)
    private boolean requestModeration;

    @Column(nullable = false)
    private Long views = 0L;

    @Column(nullable = false)
    private Integer confirmedRequests = 0;

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
