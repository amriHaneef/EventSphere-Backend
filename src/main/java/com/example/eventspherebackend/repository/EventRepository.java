package com.example.eventspherebackend.repository;

import com.example.eventspherebackend.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, String> {
    // Add custom query methods if required, e.g.:
    // List<Event> findByBatchId(String batchId);
}
