package com.example.eventspherebackend.repository;

import com.example.eventspherebackend.model.BatchEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface BatchEventRepository extends JpaRepository<BatchEvent, String> {

    List<BatchEvent> findEventByBatchIdAndEventEventDate(int Id, Date eventDate);

    List<BatchEvent> findBatchByEventId(int Id);

    List<BatchEvent> findEventByBatchId(int batchId);

    void deleteByEventIdAndBatchId(int eventId, int batchId);
}
