package com.example.eventspherebackend.service;

import com.example.eventspherebackend.model.StudentEvent;
import com.example.eventspherebackend.model.Users;
import com.example.eventspherebackend.repository.EventRepository;
import com.example.eventspherebackend.repository.StudentEventRepository;
import com.example.eventspherebackend.repository.UserRepository;
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

    public void assignStudent(Long eventId, Long studentId) {
        StudentEvent studentEvent = new StudentEvent();
        studentEvent.setEvent(EventRepository.findById(String.valueOf(eventId)).orElse(null));
        studentEvent.setStudent(UserRepository.findById(String.valueOf(studentId)).orElse(null));
        StudentEventRepository.save(studentEvent);
    }

    public List<Users> getAsignedStudents(Long id) {
        List<StudentEvent> studentEventList = StudentEventRepository.findStudentByEventId(id);
        List<Users> studentList = new ArrayList<>();
        for (StudentEvent studentEvent : studentEventList) {
            studentList.add(studentEvent.getStudent());
        }
        return studentList;
    }
}
