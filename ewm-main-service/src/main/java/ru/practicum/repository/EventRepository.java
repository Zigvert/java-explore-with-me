package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.model.Event;
import ru.practicum.model.EventStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("SELECT e FROM Event e " +
            "WHERE e.status = :status " +
            "AND (:text IS NULL OR LOWER(e.annotation) LIKE LOWER(CONCAT('%', :text, '%')) OR LOWER(e.description) LIKE LOWER(CONCAT('%', :text, '%'))) " +
            "AND (:categoryIds IS NULL OR e.category.id IN :categoryIds) " +
            "AND (:paid IS NULL OR e.isPaid = :paid) " +
            "AND e.eventDate BETWEEN :start AND :end " +
            "AND (:onlyAvailable = FALSE OR e.participantLimit = 0 OR (SELECT COUNT(r) FROM ParticipationRequest r WHERE r.event.id = e.id AND r.status = 'CONFIRMED') < e.participantLimit) " +
            "ORDER BY CASE WHEN :sort = 'EVENT_DATE' THEN e.eventDate ELSE (SELECT COUNT(h) FROM EndpointHit h WHERE h.uri = CONCAT('/events/', e.id)) END")
    List<Event> findEvents(@Param("text") String text,
                           @Param("categoryIds") List<Long> categoryIds,
                           @Param("paid") Boolean paid,
                           @Param("start") LocalDateTime start,
                           @Param("end") LocalDateTime end,
                           @Param("onlyAvailable") boolean onlyAvailable,
                           @Param("sort") String sort,
                           @Param("from") int from,
                           @Param("size") int size);
}