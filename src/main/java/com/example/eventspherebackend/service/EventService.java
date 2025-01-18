package com.example.eventspherebackend.service;

import com.example.eventspherebackend.dto.AttendenceDTO;
import com.example.eventspherebackend.dto.EventDTO;
import com.example.eventspherebackend.dto.FeedbackDTO;
import com.example.eventspherebackend.dto.UsersDTO;
import com.example.eventspherebackend.model.*;
import com.example.eventspherebackend.repository.*;
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
    final private BatchEventRepository BatchEventRepository;
    final private StudentBatchRepository StudentBatchRepository;
    final private FeedbackRepository FeedbackRepository;
    final private AttendanceRepository attendanceRepository;


    public EventService(EventRepository eventRepository, StudentEventRepository studentEventRepository, UserRepository userRepository, BatchEventRepository batchEventRepository, StudentBatchRepository studentBatchRepository, com.example.eventspherebackend.repository.FeedbackRepository feedbackRepository, AttendanceRepository attendanceRepository) {
        EventRepository = eventRepository;
        StudentEventRepository = studentEventRepository;
        UserRepository = userRepository;
        BatchEventRepository = batchEventRepository;
        StudentBatchRepository = studentBatchRepository;
        FeedbackRepository = feedbackRepository;
        this.attendanceRepository = attendanceRepository;
    }

    public List<EventDTO> getAllEvents(Date eventDate) {

        List<Event> eventList;
        List<StudentEvent> studentEventList;
        List<BatchEvent> batchEventList;
        String role = JwtUtil.role;

        if(role.equals("ADMIN")) {
           eventList = EventRepository.findByEventDate(eventDate);
        } else if(role.equals("TEACHER")) {
            eventList = EventRepository.findByCoordinatorUsernameAndEventDate(JwtUtil.username,eventDate);
        } else if(role.equals("STUDENT")) {
            studentEventList = StudentEventRepository.findByStudentUsernameAndEventEventDate(JwtUtil.username, eventDate);
            batchEventList = BatchEventRepository.findEventByBatchIdAndEventEventDate(StudentBatchRepository.findByStudentUsername(JwtUtil.username).getBatch().getId(), eventDate);
            eventList = studentEventList.stream()
                    .map(StudentEvent::getEvent)
                    .collect(Collectors.toList());
            eventList.addAll(batchEventList.stream()
                    .map(BatchEvent::getEvent)
                    .collect(Collectors.toList()));
        }
        else {
            return null;
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

    public void deleteEvent(int id) {
        EventRepository.deleteById(String.valueOf(id));
    }

    //get assined student from batch
    public List<UsersDTO> getBatchStudent(int eventId)
    {
        List<BatchEvent> eventBatches = BatchEventRepository.findBatchByEventId(eventId);
        List<Batch> batches = eventBatches.stream().map(BatchEvent::getBatch).collect(Collectors.toList());
        List<StudentBatch> studentBatches = List.of();

        for(Batch batch : batches) {
            studentBatches = StudentBatchRepository.findByBatchId(batch.getId());
        }

        List<Users> users = studentBatches.stream().map(StudentBatch::getStudent).collect(Collectors.toList());
        List<UsersDTO> usersDTOS = users.stream().map(this::toDto).collect(Collectors.toList());
        return usersDTOS;

    }

    //add feedback to event
    public void addFeedback(String eventId, String targettype, String feedback) {
        Feedback feedback1 = new Feedback();
        feedback1.setEvent(EventRepository.findById(eventId).orElse(null));
        feedback1.setFeedback(feedback);
        feedback1.setTargetType(targettype);

        FeedbackRepository.save(feedback1);
    }

    //get all feedbacks for an event
    public List<FeedbackDTO> getFeedbacksForEvent(String eventId) {
        List<Feedback> feedbackList = FeedbackRepository.findByEventId(Integer.parseInt(eventId));
        List<FeedbackDTO> feedbackDTOList = new ArrayList<>();
        for (Feedback feedback : feedbackList) {
            FeedbackDTO feedbackDTO = new FeedbackDTO();
            feedbackDTO.setFeedbackId(String.valueOf(feedback.getId()));
            feedbackDTO.setEventId(feedback.getEvent().getId());
            feedbackDTO.setFeedback(feedback.getFeedback());
            feedbackDTO.setTargetType(feedback.getTargetType());
            feedbackDTOList.add(feedbackDTO);
        }
        return feedbackDTOList;
    }

    private UsersDTO toDto(Users user)
    {
        UsersDTO dto = new UsersDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setStatus(user.getStatus());
        return dto;
    }

    public List<EventDTO> getAll() {
        List<Event> events = EventRepository.findAll();
        List<EventDTO> eventDTOs = new ArrayList<>();

        for (Event event : events) {
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
            eventDTOs.add(eventDTO);
        }
        return eventDTOs;

    }
}
