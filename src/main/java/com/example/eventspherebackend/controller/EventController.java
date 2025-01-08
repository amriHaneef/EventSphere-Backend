package com.example.eventspherebackend.controller;

import com.example.eventspherebackend.dto.BatchDTO;
import com.example.eventspherebackend.dto.EventDTO;
import com.example.eventspherebackend.dto.FeedbackDTO;
import com.example.eventspherebackend.dto.UsersDTO;
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

    public EventController(EventService eventService, BatchEventService batchEventService, StudentEventService studentEventService) {
        this.eventService = eventService;
        this.batchEventService = batchEventService;
        this.studentEventService = studentEventService;
    }

    @GetMapping("/getAllEvents")
    public List<EventDTO> getAllEvents(@RequestParam("eventDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date eventDate) {
        System.out.println("Event Date: " + eventDate);
        return eventService.getAllEvents(eventDate);
    }

    @GetMapping("/getEventById")
    public EventDTO getEventById(@RequestParam("eventId") String eventId) {
        return eventService.getEventById(Integer.parseInt(eventId));
    }

    @PostMapping("/addEvent")
    @PreAuthorize("hasRole('Teacher')")
    public String addEvent(@RequestBody EventDTO eventDTO) {
        eventService.addEvent(eventDTO);
        return "Event added successfully";
    }

    @PostMapping("/updateEvent")
    @PreAuthorize("hasRole('Teacher')")
    public String updateEvent(@RequestBody EventDTO eventDTO) {
        eventService.updateEvent(eventDTO);
        return "Event updated successfully";
    }

    @PostMapping("/deleteEvent")
    @PreAuthorize("hasRole('Teacher')")
    public String deleteEvent(@RequestParam("eventId") String eventId) {
        eventService.deleteEvent(Integer.parseInt(eventId));
        return "Event deleted successfully";
    }

    @PostMapping("addBatch")
    @PreAuthorize("hasRole('Teacher')")
    public String addBatch(@RequestBody Map<String, String> request) {
        batchEventService.assignBatch(request.get("eventId"), request.get("batchId"));
        return "Batch added successfully";
    }

    @GetMapping("/getEventBatches")
    public ResponseEntity<?> getBatches(@RequestParam("eventId") int eventId) {
        try {
            List<BatchDTO> batches = batchEventService.getAsignedBatches(eventId);
            return ResponseEntity.ok(batches);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/removeBatch")
    @PreAuthorize("hasRole('Teacher')")
    public String removeBatch(@RequestBody Map<String, String> request) {
        batchEventService.removeBatch(request.get("eventId"), request.get("batchId"));
        return "Batch removed successfully";
    }

    @PostMapping("/addStudent")
    @PreAuthorize("hasRole('Teacher')")
    public String addStudent(@RequestBody Map<String, String> request) {
        studentEventService.assignStudent(request.get("eventId"), request.get("studentId"));
        return "Student added successfully";
    }

    @GetMapping("/getEventStudents")
    public ResponseEntity<?> getStudentEvents(@RequestParam("eventId") int eventId) {
        try {
            List<UsersDTO> events = studentEventService.getAsignedStudents(eventId);
            return ResponseEntity.ok(events);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/removeStudent")
    @PreAuthorize("hasRole('Teacher')")
    public String removeStudent(@RequestBody Map<String, String> request) {
        studentEventService.removeStudent(request.get("eventId"), request.get("studentId"));
        return "Student removed successfully";
    }

    @PostMapping("/addFeedback")
    public String addFeedback(@RequestBody Map<String, String> request) {
        eventService.addFeedback(request.get("eventId"), request.get("targetType"), request.get("feedback"));
        return "Feedback added successfully";
    }

    @GetMapping("/getFeedbacks")
    public ResponseEntity<?> getFeedbacks(@RequestParam("eventId") String eventId) {
        try {
            List<FeedbackDTO> feedbacks = eventService.getFeedbacksForEvent(eventId);
            return ResponseEntity.ok(feedbacks);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

}