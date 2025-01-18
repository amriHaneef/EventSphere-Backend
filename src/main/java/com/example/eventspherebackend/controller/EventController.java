package com.example.eventspherebackend.controller;

import com.example.eventspherebackend.dto.*;
import com.example.eventspherebackend.service.AttendanceService;
import com.example.eventspherebackend.service.BatchEventService;
import com.example.eventspherebackend.service.EventService;
import com.example.eventspherebackend.service.StudentEventService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private final BatchEventService batchEventService;
    private final StudentEventService studentEventService;
    private final AttendanceService attendanceService;

    public EventController(EventService eventService, BatchEventService batchEventService, StudentEventService studentEventService, AttendanceService attendanceService) {
        this.eventService = eventService;
        this.batchEventService = batchEventService;
        this.studentEventService = studentEventService;
        this.attendanceService = attendanceService;
    }

    // Get all events
    @GetMapping("/getAllEvents")
    public ResponseEntity<?> getAllEvents(@RequestParam("eventDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date eventDate) {
        try {
            System.out.println("Event Date: " + eventDate);
            return ResponseEntity.ok(eventService.getAllEvents(eventDate));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching events: " + e.getMessage());
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllEvents() {
        try {
            return ResponseEntity.ok(eventService.getAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching events: " + e.getMessage());
        }
    }

    // Get event by ID
    @GetMapping("/getEventById")
    public ResponseEntity<?> getEventById(@RequestParam("eventId") String eventId) {
        try {
            return ResponseEntity.ok(eventService.getEventById(Integer.parseInt(eventId)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching event by ID: " + e.getMessage());
        }
    }

    // Add an event
    @PostMapping("/addEvent")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> addEvent(@RequestBody EventDTO eventDTO) {
        try {
            eventService.addEvent(eventDTO);
            return ResponseEntity.ok("Event added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error adding event: " + e.getMessage());
        }
    }

    // Update an event
    @PutMapping("/updateEvent")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> updateEvent(@RequestBody EventDTO eventDTO) {
        try {
            eventService.updateEvent(eventDTO);
            return ResponseEntity.ok("Event updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating event: " + e.getMessage());
        }
    }

    // Delete an event
    @DeleteMapping("/deleteEvent")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> deleteEvent(@RequestParam("eventId") String eventId) {
        try {
            eventService.deleteEvent(Integer.parseInt(eventId));
            return ResponseEntity.ok("Event deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting event: " + e.getMessage());
        }
    }

    // Add batch to event
    @PostMapping("/addBatch")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> addBatch(@RequestBody EventBatchDTO eventBatchDTO) {
        try {
            batchEventService.assignBatch(eventBatchDTO.getEventId(), eventBatchDTO.getBatchIds());
            return ResponseEntity.ok("Batch added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error adding batch: " + e.getMessage());
        }
    }

    // Get all batches in an event
    @GetMapping("/getEventBatches")
    public ResponseEntity<?> getBatches(@RequestParam("eventId") int eventId) {
        try {
            List<BatchDTO> batches = batchEventService.getAsignedBatches(eventId);
            return ResponseEntity.ok(batches);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching batches: " + e.getMessage());
        }
    }

    // Remove batch from event
    @DeleteMapping("/removeBatch")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> removeBatch(@RequestBody Map<String, String> request) {
        try {
            batchEventService.removeBatch(request.get("eventId"), request.get("batchId"));
            return ResponseEntity.ok("Batch removed successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error removing batch: " + e.getMessage());
        }
    }

    // Add student to event
    @PostMapping("/addStudent")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> addStudent(@RequestBody EventStudentDTO eventStudentDTO) {
        try {
            studentEventService.assignStudent(eventStudentDTO.getEventId(), eventStudentDTO.getStudentIds());
            return ResponseEntity.ok("Student added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error adding student: " + e.getMessage());
        }
    }

    // Get all students in an event
    @GetMapping("/getEventStudents")
    @PreAuthorize("hasRole('TEACHER') OR hasRole('ADMIN')")
    public ResponseEntity<?> getStudentEvents(@RequestParam("eventId") int eventId) {
        try {
            List<UsersDTO> students = studentEventService.getAsignedStudents(eventId);
            students.addAll(eventService.getBatchStudent(eventId));
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching students: " + e.getMessage());
        }
    }

    @DeleteMapping("/removeStudent")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> removeStudent(@RequestBody Map<String, String> request) {
        try {
            studentEventService.removeStudent(request.get("eventId"), request.get("studentId"));
            return ResponseEntity.ok("Student removed successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error removing student: " + e.getMessage());
        }
    }

    @PostMapping("/addFeedback")
    public ResponseEntity<?> addFeedback(@RequestBody Map<String, String> request) {
        try {
            eventService.addFeedback(request.get("eventId"), request.get("targetType"), request.get("feedback"));
            return ResponseEntity.ok("Feedback added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error adding feedback: " + e.getMessage());
        }
    }

    @GetMapping("/getFeedbacks")
    public ResponseEntity<?> getFeedbacks(@RequestParam("eventId") String eventId) {
        try {
            List<FeedbackDTO> feedbacks = eventService.getFeedbacksForEvent(eventId);
            return ResponseEntity.ok(feedbacks);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching feedbacks: " + e.getMessage());
        }
    }

    @PostMapping("/markAttendance")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> markAttendance(@RequestBody List<AttendenceDTO> attendenceDTO) {
        try {
            attendanceService.markAttendance(attendenceDTO);
            return ResponseEntity.ok("Attendance marked successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error marking attendance: " + e.getMessage());
        }
    }

    @GetMapping("/getAttendance")
    @PreAuthorize("hasRole('TEACHER') OR hasRole('ADMIN')" )
    public ResponseEntity<?> getAttendance(@RequestParam("eventId") int eventId) {
        try {
            List<AttendenceDTO> attendenceDTO = attendanceService.getAttendanceForEvent(String.valueOf(eventId));
            return ResponseEntity.ok(attendenceDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching attendance: " + e.getMessage());
        }
    }

    @PutMapping("/updateAttendance")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> updateAttendance(@RequestBody List<AttendenceDTO> attendenceDTO) {
        try {
            attendanceService.updateAttendance(attendenceDTO);
            return ResponseEntity.ok("Attendance updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating attendance: " + e.getMessage());
        }
    }
}
