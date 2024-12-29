package com.example.eventspherebackend.repository;

import com.example.eventspherebackend.model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchRepository extends JpaRepository<Batch, String> {
    // Add custom query methods if required
}