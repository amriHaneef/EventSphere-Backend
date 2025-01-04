package com.example.eventspherebackend.repository;

import com.example.eventspherebackend.model.BatchEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BatchEventRepository extends JpaRepository<BatchEvent, String> {

    List<BatchEvent> findEventByBatchId(String Id);

//    List<BatchEvent> findBatchByEventId(String id);
}
