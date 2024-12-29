package com.example.eventspherebackend.repository;

import com.example.eventspherebackend.model.StudentBatch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentBatchRepository extends JpaRepository<StudentBatch, String> {
    // Add custom query methods if required
}
