package com.example.eventspherebackend.service;

import com.example.eventspherebackend.repository.StudentBatchRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentBatchService {
    private final StudentBatchRepository studentBatchRepository;

    public StudentBatchService(StudentBatchRepository studentBatchRepository) {
        this.studentBatchRepository = studentBatchRepository;
    }

    //add Student to Batch

}
