package com.example.eventspherebackend.service;

import com.example.eventspherebackend.dto.UsersDTO;
import com.example.eventspherebackend.model.Batch;
import com.example.eventspherebackend.model.StudentBatch;
import com.example.eventspherebackend.model.Users;
import com.example.eventspherebackend.repository.BatchRepository;
import com.example.eventspherebackend.repository.StudentBatchRepository;
import com.example.eventspherebackend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    @Transactional
    public void removeStudentFromBatch(String batchId, List<String> studentIds) {
        for (String studentId : studentIds) {
            studentBatchRepository.deleteByBatchIdAndStudentId(
                    Integer.parseInt(batchId),
                    Integer.parseInt(studentId)
            );
        }
    }

    //get all students in a batch
    public List<UsersDTO> getStudentsInBatch(String batchId) {
        List<StudentBatch> batchStudents = studentBatchRepository.findByBatchId(Integer.parseInt(batchId));
        List<UsersDTO> students = new ArrayList<>();

        for(StudentBatch studentBatch : batchStudents) {
            students.add(toUsersDTO(studentBatch.getStudent()));
        }
        return students;
    }

    // Convert a User entity to a UserDTO
    public UsersDTO toUsersDTO(Users user) {
        if (user == null) {
            return null; // Return null if the user object is null
        }

        UsersDTO dto = new UsersDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setDob(user.getDob());
        dto.setAge(user.getAge());
        dto.setStatus(user.getStatus());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }


}
