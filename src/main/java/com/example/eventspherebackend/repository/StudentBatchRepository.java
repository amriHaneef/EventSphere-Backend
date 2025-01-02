package com.example.eventspherebackend.repository;

import com.example.eventspherebackend.dto.EventDTO;
import com.example.eventspherebackend.model.StudentBatch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentBatchRepository extends JpaRepository<StudentBatch, String> {
    // Add custom query methods if required
//    List<EventDTO> findEventByUsername(String username);
}
