package ru.practicum.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.model.Event;
import ru.practicum.model.EventStatus;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

        @Query("SELECT e FROM Event e " +
                "WHERE (:status IS NULL OR e.status = :status) " +
                "AND (:text IS NULL OR LOWER(e.annotation) LIKE LOWER(CONCAT('%', :text, '%')) " +
                "   OR LOWER(e.description) LIKE LOWER(CONCAT('%', :text, '%'))) " +
                "AND (:categoryIds IS NULL OR e.category.id IN :categoryIds) " +
                "AND (:paid IS NULL OR e.paid = :paid) " +
                "AND (:rangeStart IS NULL OR e.eventDate >= :rangeStart) " +
                "AND (:rangeEnd IS NULL OR e.eventDate <= :rangeEnd) " +
                "AND (:onlyAvailable = FALSE OR e.participantLimit = 0 OR e.confirmedRequests < e.participantLimit)")
        List<Event> findEvents(@Param("status") EventStatus status,
                               @Param("text") String text,
                               @Param("categoryIds") List<Long> categoryIds,
                               @Param("paid") Boolean paid,
                               @Param("rangeStart") LocalDateTime rangeStart,
                               @Param("rangeEnd") LocalDateTime rangeEnd,
                               @Param("onlyAvailable") boolean onlyAvailable,
                               Pageable pageable);

        List<Event> findByInitiatorId(Long initiatorId, Pageable pageable);

        List<Event> findByPaid(Boolean paid, Pageable pageable);
    }