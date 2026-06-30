package com.evenza.event_service.repository;

import com.evenza.event_service.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event , Integer> {
    List<Event> findByTitleContainingIgnoreCase (String title);
}
