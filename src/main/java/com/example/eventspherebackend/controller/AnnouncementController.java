package com.example.eventspherebackend.controller;

import com.example.eventspherebackend.dto.*;
import com.example.eventspherebackend.model.Announcement;
import com.example.eventspherebackend.model.BatchAnnoun;
import com.example.eventspherebackend.model.StudentAnnoun;
import com.example.eventspherebackend.repository.BatchAnnounRepository;
import com.example.eventspherebackend.service.AnnouncementService;
import com.example.eventspherebackend.service.BatchAnnounService;
import com.example.eventspherebackend.service.StudentAnnounService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Announcement")
public class AnnouncementController {
    final private AnnouncementService AnnouncementService;
    final private StudentAnnounService studentAnnounService;
    final private BatchAnnounService batchAnnounService;

    public AnnouncementController(AnnouncementService announcementService, StudentAnnounService studentAnnounService, BatchAnnounService batchAnnounService) {
        AnnouncementService = announcementService;
        this.studentAnnounService = studentAnnounService;
        this.batchAnnounService = batchAnnounService;
    }

    //get all announcements
    @GetMapping("/getAll")
    public List<AnnouncementDTO> getAllAnnouncements() {

        return AnnouncementService.getAllAnnouncements();
    }

    //add an announcement
    @PostMapping("/add")
    public String addAnnouncement(@RequestBody AnnouncementDTO announcementDTO) {
        try {
            AnnouncementService.addAnnouncement(announcementDTO);
            return "Announcement added successfully";
        }
        catch (Exception e) {
            return "Error adding announcement"+e.getMessage();
        }

    }

    //add student to announcement
    @PostMapping("/addStudent")
    public String addStudentToAnnouncement(@RequestBody StudentAnnounDTO studentAnnounDTO) {
        studentAnnounService.addStudentAnnouncement(studentAnnounDTO.getAnnouncementId(), studentAnnounDTO.getStudentIds());
        return "Student added to announcement successfully";
    }

    //remove student from announcement
    @PostMapping("/removeStudent")
    public String removeStudentFromAnnouncement(@RequestBody StudentAnnounDTO studentAnnounDTO) {
        studentAnnounService.removeStudentAnnouncement(studentAnnounDTO.getAnnouncementId(), studentAnnounDTO.getStudentIds());
        return "Student removed from announcement successfully";
    }

    //add batch to announcement
    @PostMapping("/addBatch")
    public String addBatchesToAnnouncement(@RequestBody BatchAnnounDTO batchAnnounDTO) {
        batchAnnounService.addBatchAnnouncements(batchAnnounDTO.getAnnouncementId(), batchAnnounDTO.getBatchIds());
        return "Batches added to announcement successfully";
    }

    //remove batch from announcement
    @PostMapping("/removeBatch")
    public String removeBatchFromAnnouncement(@RequestBody BatchAnnounDTO batchAnnounDTO) {
        batchAnnounService.removeBatchAnnouncement(batchAnnounDTO.getAnnouncementId(), batchAnnounDTO.getBatchIds());
        return "Batch removed from announcement successfully";
    }

    //get all students in announcement
    @GetMapping("/getStudents")
    public List<UsersDTO> getStudentsInAnnouncement(@RequestParam("announcementId") int announcementId) {
        return studentAnnounService.getStudentsInAnnouncement(announcementId);
    }

    //get all batches in announcement
    @GetMapping("/getBatches")
    public List<BatchDTO> getBatchesInAnnouncement(@RequestParam("announcementId") int announcementId) {
        return batchAnnounService.getBatchesInAnnouncement(announcementId);
    }

}
