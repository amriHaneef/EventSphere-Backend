package com.example.eventspherebackend.repository;

import com.example.eventspherebackend.model.BatchAnnoun;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BatchAnnounRepository extends JpaRepository<BatchAnnoun, String> {
    List<BatchAnnoun> findByBatchId(int batchId);

    void deleteByAnnouncementIdAndBatchId(int announId, int batchId);
}
