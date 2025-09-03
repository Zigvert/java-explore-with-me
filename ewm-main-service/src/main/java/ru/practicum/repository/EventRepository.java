package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import ru.practicum.model.Event;
import ru.practicum.model.EventStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("SELECT e FROM Event e " +
            "WHERE e.status = :status " +
            "AND (:text IS NULL OR LOWER(e.annotation) LIKE LOWER(CONCAT('%', :text, '%')) " +
            "OR LOWER(e.description) LIKE LOWER(CONCAT('%', :text, '%'))) " +
            "AND (:categoryIds IS NULL OR e.category.id IN :categoryIds) " +
            "AND (:paid IS NULL OR e.paid = :paid) " +
            "AND e.eventDate BETWEEN :start AND :end " +
            "AND (:onlyAvailable = FALSE OR e.participantLimit = 0 " +
            "OR e.confirmedRequests < e.participantLimit)")
    List<Event> findEvents(@Param("status") EventStatus status,
                           @Param("text") String text,
                           @Param("categoryIds") List<Long> categoryIds,
                           @Param("paid") Boolean paid,
                           @Param("start") LocalDateTime start,
                           @Param("end") LocalDateTime end,
                           @Param("onlyAvailable") boolean onlyAvailable,
                           Pageable pageable);

    List<Event> findByInitiatorId(Long initiatorId, Pageable pageable);

    List<Event> findByPaid(Boolean paid, Pageable pageable);
}
