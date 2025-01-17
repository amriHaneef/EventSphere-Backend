package com.example.eventspherebackend.repository;

import com.example.eventspherebackend.dto.BatchDTO;
import com.example.eventspherebackend.model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BatchRepository extends JpaRepository<Batch, String> {
    List<Batch> findByConsultantId(int consultantId);

    List<Batch> findBatchByConsultantUsername(String username);
    // Add custom query methods if required
}