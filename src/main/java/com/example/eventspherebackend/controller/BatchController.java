package com.example.eventspherebackend.controller;

import com.example.eventspherebackend.dto.BatchDTO;
import com.example.eventspherebackend.dto.StudentBatchDTO;
import com.example.eventspherebackend.dto.UsersDTO;
import com.example.eventspherebackend.service.BatchService;
import com.example.eventspherebackend.service.StudentBatchService;
import com.example.eventspherebackend.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/batch")
public class BatchController {
    private final BatchService batchService;
    private final StudentBatchService studentBatchService;

    public BatchController(BatchService batchService, StudentBatchService studentBatchService) {
        this.batchService = batchService;
        this.studentBatchService = studentBatchService;
    }

    // Get all batches
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllBatches() {
        try {
            return ResponseEntity.ok(batchService.getAllBatches());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching batches: " + e.getMessage());
        }
    }

    // Get batch by its ID
    @GetMapping("/getBatchById")
    public ResponseEntity<BatchDTO> getBatchById(@RequestParam("batchId") String id) {
        try {
            return ResponseEntity.ok(batchService.getBatchById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null); // Return null for batch if not found
        }
    }

    // Get batches by consultant's ID
    @GetMapping("/getBatchByConsultant")
    public ResponseEntity<List<BatchDTO>> getBatchByConsultantId() {
        try {
            return ResponseEntity.ok(batchService.getBatchByConsultantId(JwtUtil.username));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null); // Return null when error occurs
        }
    }

    // Add a new batch
    @PostMapping("/add")
    @PreAuthorize("hasRole('TEACHER') OR hasRole('ADMIN')")
    public ResponseEntity<String> addBatch(@RequestBody BatchDTO batchDTO) {
        try {
            batchService.addBatch(batchDTO);
            return ResponseEntity.status(201).body("Batch added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error adding batch: " + e.getMessage());
        }
    }

    // Add student to a batch
    @PostMapping("/addStudent")
    @PreAuthorize("hasRole('TEACHER') OR hasRole('ADMIN')")
    public ResponseEntity<String> addStudentToBatch(@RequestBody StudentBatchDTO studentBatchDTO) {
        try {
            studentBatchService.addStudentToBatch(studentBatchDTO.getBatchId(), studentBatchDTO.getStudentIds());
            return ResponseEntity.ok("Student added to batch successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error adding student to batch: " + e.getMessage());
        }
    }

    // Remove student from a batch
    @DeleteMapping("/removeStudent")
    @PreAuthorize("hasRole('TEACHER') OR hasRole('ADMIN')")
    public ResponseEntity<String> removeStudentFromBatch(@RequestBody StudentBatchDTO studentBatchDTO) {
        try {
            studentBatchService.removeStudentFromBatch(studentBatchDTO.getBatchId(), studentBatchDTO.getStudentIds());
            return ResponseEntity.ok("Student removed from batch successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error removing student from batch: " + e.getMessage());
        }
    }

    // Get students in a specific batch
    @GetMapping("/getStudents")
    public ResponseEntity<List<UsersDTO>> getStudentsInBatch(@RequestParam("batchId") String batchId) {
        try {
            return ResponseEntity.ok(studentBatchService.getStudentsInBatch(batchId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null); // Return null when error occurs
        }
    }

    // Update an existing batch
    @PutMapping("/update")
    @PreAuthorize("hasRole('TEACHER') OR hasRole('ADMIN')")
    public ResponseEntity<String> updateBatch(@RequestBody BatchDTO batchDTO) {
        try {
            batchService.updateBatch(batchDTO);
            return ResponseEntity.ok("Batch updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating batch: " + e.getMessage());
        }
    }

    // Delete a batch
    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('TEACHER') OR hasRole('ADMIN')")
    public ResponseEntity<String> deleteBatch(@RequestParam("batchId") String id) {
        try {
            batchService.deleteBatch(id);
            return ResponseEntity.ok("Batch deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting batch: " + e.getMessage());
        }
    }
}
