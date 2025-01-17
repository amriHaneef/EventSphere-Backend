package com.example.eventspherebackend.controller;

import com.example.eventspherebackend.dto.*;
import com.example.eventspherebackend.model.Announcement;
import com.example.eventspherebackend.service.AnnouncementService;
import com.example.eventspherebackend.service.BatchAnnounService;
import com.example.eventspherebackend.service.StudentAnnounService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Announcement")
public class AnnouncementController {
    final private AnnouncementService announcementService;
    final private StudentAnnounService studentAnnounService;
    final private BatchAnnounService batchAnnounService;

    public AnnouncementController(AnnouncementService announcementService, StudentAnnounService studentAnnounService, BatchAnnounService batchAnnounService) {
        this.announcementService = announcementService;
        this.studentAnnounService = studentAnnounService;
        this.batchAnnounService = batchAnnounService;
    }

    // Get all announcements
    @GetMapping("/getAll")
    public ResponseEntity<List<AnnouncementDTO>> getAllAnnouncements() {
        try {
            return ResponseEntity.ok(announcementService.getAllAnnouncements());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null); // Return null on error
        }
    }

    // Add an announcement
    @PostMapping("/add")
    @PreAuthorize("hasRole('TEACHER') OR hasRole('ADMIN')")
    public ResponseEntity<String> addAnnouncement(@RequestBody AnnouncementDTO announcementDTO) {
        try {
            announcementService.addAnnouncement(announcementDTO);
            return ResponseEntity.status(201).body("Announcement added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error adding announcement: " + e.getMessage());
        }
    }

    // Add student to announcement
    @PostMapping("/addStudent")
    @PreAuthorize("hasRole('TEACHER') OR hasRole('ADMIN')")
    public ResponseEntity<String> addStudentToAnnouncement(@RequestBody StudentAnnounDTO studentAnnounDTO) {
        try {
            studentAnnounService.addStudentAnnouncement(studentAnnounDTO.getAnnouncementId(), studentAnnounDTO.getStudentIds());
            return ResponseEntity.ok("Student added to announcement successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error adding student to announcement: " + e.getMessage());
        }
    }

    // Remove student from announcement
    @DeleteMapping("/removeStudent")
    @PreAuthorize("hasRole('TEACHER') OR hasRole('ADMIN')")
    public ResponseEntity<String> removeStudentFromAnnouncement(@RequestBody StudentAnnounDTO studentAnnounDTO) {
        try {
            studentAnnounService.removeStudentAnnouncement(studentAnnounDTO.getAnnouncementId(), studentAnnounDTO.getStudentIds());
            return ResponseEntity.ok("Student removed from announcement successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error removing student from announcement: " + e.getMessage());
        }
    }

    // Add batch to announcement
    @PostMapping("/addBatch")
    @PreAuthorize("hasRole('TEACHER') OR hasRole('ADMIN')")
    public ResponseEntity<String> addBatchesToAnnouncement(@RequestBody BatchAnnounDTO batchAnnounDTO) {
        try {
            batchAnnounService.addBatchAnnouncements(batchAnnounDTO.getAnnouncementId(), batchAnnounDTO.getBatchIds());
            return ResponseEntity.ok("Batches added to announcement successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error adding batches to announcement: " + e.getMessage());
        }
    }

    // Remove batch from announcement
    @DeleteMapping("/removeBatch")
    @PreAuthorize("hasRole('TEACHER') OR hasRole('ADMIN')")
    public ResponseEntity<String> removeBatchFromAnnouncement(@RequestBody BatchAnnounDTO batchAnnounDTO) {
        try {
            batchAnnounService.removeBatchAnnouncement(batchAnnounDTO.getAnnouncementId(), batchAnnounDTO.getBatchIds());
            return ResponseEntity.ok("Batch removed from announcement successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error removing batch from announcement: " + e.getMessage());
        }
    }

    // Get all students in an announcement
    @GetMapping("/getStudents")
    @PreAuthorize("hasRole('TEACHER') OR hasRole('ADMIN')")
    public ResponseEntity<List<UsersDTO>> getStudentsInAnnouncement(@RequestParam("announcementId") int announcementId) {
        try {
            return ResponseEntity.ok(studentAnnounService.getStudentsInAnnouncement(announcementId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null); // Return null when error occurs
        }
    }

    // Get all batches in an announcement
    @GetMapping("/getBatches")
    @PreAuthorize("hasRole('TEACHER') OR hasRole('ADMIN')")
    public ResponseEntity<List<BatchDTO>> getBatchesInAnnouncement(@RequestParam("announcementId") int announcementId) {
        try {
            return ResponseEntity.ok(batchAnnounService.getBatchesInAnnouncement(announcementId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null); // Return null when error occurs
        }
    }
}
