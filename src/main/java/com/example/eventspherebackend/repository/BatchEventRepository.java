package com.example.eventspherebackend.repository;

import com.example.eventspherebackend.model.BatchEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchEventRepository extends JpaRepository<BatchEvent, String> {
    // Add custom query methods if required, e.g.:
    // List<BatchEvent> findByBatchId(String batchId);
}
