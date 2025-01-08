package com.example.eventspherebackend.service;

import com.example.eventspherebackend.model.Batch;
import com.example.eventspherebackend.model.StudentBatch;
import com.example.eventspherebackend.model.Users;
import com.example.eventspherebackend.repository.BatchRepository;
import com.example.eventspherebackend.repository.StudentBatchRepository;
import com.example.eventspherebackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentBatchService {
    private final StudentBatchRepository studentBatchRepository;
    private final BatchRepository batchRepository;
    private final UserRepository userRepository;


    public StudentBatchService(StudentBatchRepository studentBatchRepository, BatchRepository batchRepository, UserRepository userRepository) {
        this.studentBatchRepository = studentBatchRepository;
        this.batchRepository = batchRepository;
        this.userRepository = userRepository;
    }

    //add Student to Batch
    public void addStudentToBatch(String batchId, List<String> studentIds) {
        // Fetch the batch once
        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new IllegalArgumentException("Batch not found for ID: " + batchId));

        // Loop through student IDs and create associations
        for (String studentId : studentIds) {
            Users student = userRepository.findById(studentId)
                    .orElseThrow(() -> new IllegalArgumentException("Student not found for ID: " + studentId));

            StudentBatch studentBatch = new StudentBatch();
            studentBatch.setBatch(batch);
            studentBatch.setStudent(student);

            studentBatchRepository.save(studentBatch);
        }
    }

    //remove Student from Batch
    public void removeStudentFromBatch(String batchId, List<String> studentIds) {
        for (String studentId : studentIds) {
            studentBatchRepository.deleteByBatchIdAndStudentId(
                    Integer.parseInt(batchId),
                    Integer.parseInt(studentId)
            );
        }
    }

}
