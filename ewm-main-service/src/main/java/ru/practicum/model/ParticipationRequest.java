package ru.practicum.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "participation_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus status;  // Предполагаемый enum, см. ниже

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User requester;  // Пользователь, отправивший запрос

    @Column(nullable = false)
    private LocalDateTime created;
}