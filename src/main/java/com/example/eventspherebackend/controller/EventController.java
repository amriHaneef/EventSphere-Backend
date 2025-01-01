package com.example.eventspherebackend.controller;

import com.example.eventspherebackend.dto.EventDTO;
import com.example.eventspherebackend.service.EventService;
import com.example.eventspherebackend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
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

}