package com.example.eventspherebackend.repository;

import com.example.eventspherebackend.model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchRepository extends JpaRepository<Batch, String> {
    Batch findByConsultantId(int consultantId);
    // Add custom query methods if required
}