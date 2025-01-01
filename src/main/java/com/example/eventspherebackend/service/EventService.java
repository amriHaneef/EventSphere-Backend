package com.example.eventspherebackend.service;

import com.example.eventspherebackend.dto.EventDTO;
import com.example.eventspherebackend.model.Event;
import com.example.eventspherebackend.repository.EventRepository;
import com.example.eventspherebackend.util.JwtUtil;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EventService {
    final private EventRepository EventRepository;

    public EventService(com.example.eventspherebackend.repository.EventRepository eventRepository) {
        EventRepository = eventRepository;
    }

    public List<EventDTO> getAllEvents(Date eventDate) {

        List<Event> eventList;
        String role = JwtUtil.role;

        if(role.equals("Admin")) {
           eventList = EventRepository.findByEventDate(eventDate);
        } else {
            eventList = EventRepository.findByEventDate(eventDate);
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
}
