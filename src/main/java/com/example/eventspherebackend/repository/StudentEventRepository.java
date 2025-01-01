package com.example.eventspherebackend.repository;

import com.example.eventspherebackend.model.StudentEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentEventRepository extends JpaRepository<StudentEvent, String> {
    // Add custom query methods if required, e.g.:
    // List<StudentEvent> findByStudentId(String studentId);
}
