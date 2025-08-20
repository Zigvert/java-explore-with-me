package ru.practicum.model;

import jakarta.persistence.*;
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

    @Column(nullable = false)
    private String title;

    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User initiator;

    private LocalDateTime eventDate;
    private LocalDateTime createdAt;
    private LocalDateTime publishedAt;

    @Enumerated(EnumType.STRING)
    private EventStatus status;

    private boolean isPaid;
    private int participantLimit;
}
