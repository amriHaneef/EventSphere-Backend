package com.example.eventspherebackend.repository;

import com.example.eventspherebackend.dto.EventDTO;
import com.example.eventspherebackend.model.StudentBatch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentBatchRepository extends JpaRepository<StudentBatch, String> {

    StudentBatch findByStudentUsername(String username);

    void deleteByBatchIdAndStudentId(int batchid, int studentId);
}
