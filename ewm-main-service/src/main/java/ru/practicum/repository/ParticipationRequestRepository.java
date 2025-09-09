package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.model.ParticipationRequest;

import java.util.List;

@Repository
public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Long> {

    // Получить все заявки пользователя
    List<ParticipationRequest> findByRequesterId(Long requesterId);

    // При желании можно добавить поиск по событию
    List<ParticipationRequest> findByEventId(Long eventId);
}
