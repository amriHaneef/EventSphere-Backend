package com.example.eventspherebackend.service;

import com.example.eventspherebackend.dto.EventDTO;
import com.example.eventspherebackend.model.Event;
import com.example.eventspherebackend.model.StudentEvent;
import com.example.eventspherebackend.repository.EventRepository;
import com.example.eventspherebackend.repository.StudentEventRepository;
import com.example.eventspherebackend.repository.UserRepository;
import com.example.eventspherebackend.util.JwtUtil;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {
    final private EventRepository EventRepository;
    final private StudentEventRepository StudentEventRepository;
    final private UserRepository UserRepository;


    public EventService(com.example.eventspherebackend.repository.EventRepository eventRepository, com.example.eventspherebackend.repository.StudentEventRepository studentEventRepository, com.example.eventspherebackend.repository.UserRepository userRepository) {
        EventRepository = eventRepository;
        StudentEventRepository = studentEventRepository;
        UserRepository = userRepository;

    }

    public List<EventDTO> getAllEvents(Date eventDate) {

        List<Event> eventList;
        List<StudentEvent> studentEventList;
        String role = JwtUtil.role;

        if(role.equals("Admin")) {
           eventList = EventRepository.findByEventDate(eventDate);
        } else if(role.equals("Teacher")) {
            eventList = EventRepository.findByCoordinatorUsernameAndEventDate(JwtUtil.username,eventDate);
        } else {
            studentEventList = StudentEventRepository.findByStudentUsernameAndEventEventDate(JwtUtil.username, eventDate);
            eventList = studentEventList.stream()
                    .map(StudentEvent::getEvent)
                    .collect(Collectors.toList());
        }

        List<EventDTO> eventDTOList = new ArrayList<>();

        for (Event event : eventList) {
            EventDTO eventDTO = new EventDTO();
            eventDTO.setId(event.getId());
            eventDTO.setTitle(event.getTitle());
            eventDTO.setType(event.getType());
            eventDTO.setTimePeriod(event.getTimePeriod());
            eventDTO.setSessionLink(event.getSessionLink());
            eventDTO.setPlatform(event.getPlatform());
            eventDTO.setCoordinatorId(String.valueOf(event.getCoordinator() != null ? event.getCoordinator().getId() : null));
            eventDTO.setCoordinatorName(event.getCoordinator() != null ? event.getCoordinator().getName() : null);
            eventDTO.setCreatedAt(event.getCreatedAt());
            eventDTO.setUpdatedAt(event.getUpdatedAt());
            eventDTO.setStatus(event.getStatus());
            eventDTO.setEventDate(event.getEventDate());

            eventDTOList.add(eventDTO);
        }

        return eventDTOList;
    }

    public EventDTO getEventById(int id) {
        Event event = EventRepository.findById(String.valueOf(id)).orElse(null);
        if (event == null) {
            return null;
        }

        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(event.getId());
        eventDTO.setTitle(event.getTitle());
        eventDTO.setType(event.getType());
        eventDTO.setTimePeriod(event.getTimePeriod());
        eventDTO.setSessionLink(event.getSessionLink());
        eventDTO.setPlatform(event.getPlatform());
        eventDTO.setCoordinatorId(String.valueOf(event.getCoordinator() != null ? event.getCoordinator().getId() : null));
        eventDTO.setCoordinatorName(event.getCoordinator() != null ? event.getCoordinator().getName() : null);
        eventDTO.setCreatedAt(event.getCreatedAt());
        eventDTO.setUpdatedAt(event.getUpdatedAt());
        eventDTO.setStatus(event.getStatus());
        eventDTO.setEventDate(event.getEventDate());

        return eventDTO;
    }

    public void addEvent(EventDTO eventDTO) {
        Event event = new Event();
        event.setTitle(eventDTO.getTitle());
        event.setType(eventDTO.getType());
        event.setTimePeriod(eventDTO.getTimePeriod());
        event.setSessionLink(eventDTO.getSessionLink());
        event.setPlatform(eventDTO.getPlatform());
        event.setCoordinator(UserRepository.findById(String.valueOf(eventDTO.getCoordinatorId())).orElse(null));
        event.setCreatedAt(eventDTO.getCreatedAt());
        event.setUpdatedAt(eventDTO.getUpdatedAt());
        event.setStatus(eventDTO.getStatus());
        event.setEventDate(eventDTO.getEventDate());

        EventRepository.save(event);
    }

    public void updateEvent(EventDTO eventDTO) {
        Event event = EventRepository.findById(String.valueOf(eventDTO.getId())).orElse(null);
        if (event == null) {
            return;
        }

        event.setTitle(eventDTO.getTitle());
        event.setType(eventDTO.getType());
        event.setTimePeriod(eventDTO.getTimePeriod());
        event.setSessionLink(eventDTO.getSessionLink());
        event.setPlatform(eventDTO.getPlatform());
        event.setCoordinator(UserRepository.findById(String.valueOf(eventDTO.getCoordinatorId())).orElse(null));
        event.setCreatedAt(eventDTO.getCreatedAt());
        event.setUpdatedAt(eventDTO.getUpdatedAt());
        event.setStatus(eventDTO.getStatus());
        event.setEventDate(eventDTO.getEventDate());

        EventRepository.save(event);
    }
}
