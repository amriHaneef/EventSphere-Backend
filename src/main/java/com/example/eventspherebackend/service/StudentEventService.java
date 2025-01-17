package com.example.eventspherebackend.service;

import com.example.eventspherebackend.dto.UsersDTO;
import com.example.eventspherebackend.model.Event;
import com.example.eventspherebackend.model.StudentEvent;
import com.example.eventspherebackend.model.Users;
import com.example.eventspherebackend.repository.EventRepository;
import com.example.eventspherebackend.repository.StudentEventRepository;
import com.example.eventspherebackend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentEventService {

    final private EventRepository EventRepository;
    final private StudentEventRepository StudentEventRepository;
    final private UserRepository UserRepository;

    public StudentEventService(StudentEventRepository studentEventRepository, EventRepository eventRepository, com.example.eventspherebackend.repository.UserRepository userRepository) {
        StudentEventRepository = studentEventRepository;
        EventRepository = eventRepository;
        UserRepository = userRepository;
    }

    //assign student to event
    public void assignStudent(String eventId, List<String> studentIds) {
        Event event = EventRepository.findById(eventId).orElseThrow(() ->
                new IllegalArgumentException("Event not found with ID: " + eventId));

        for (String studentId : studentIds) {
            Users student = UserRepository.findById(studentId).orElseThrow(() ->
                    new IllegalArgumentException("Student not found with ID: " + studentId));

            StudentEvent studentEvent = new StudentEvent();
            studentEvent.setEvent(event);
            studentEvent.setStudent(student);

            StudentEventRepository.save(studentEvent);
        }
    }


    //get all students assigned to an event
    public List<UsersDTO> getAsignedStudents(int id) {
        List<StudentEvent> studentEventList = StudentEventRepository.findStudentByEventId(id);
        List<UsersDTO> studentList = new ArrayList<>();
        for (StudentEvent studentEvent : studentEventList) {
            Users student = studentEvent.getStudent();
            UsersDTO studentDTO = convertToUsersDTO(student);
            studentList.add(studentDTO);
        }
        return studentList;
    }

    @Transactional
    public void removeStudent(String eventId, String studentId) {
        StudentEventRepository.deleteByEventIdAndStudentId(Integer.parseInt(eventId), Integer.parseInt(studentId));
    }

    // Helper method to convert Users to UsersDTO
    private UsersDTO convertToUsersDTO(Users user) {
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
