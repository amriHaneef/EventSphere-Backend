package com.example.eventspherebackend.repository;

import com.example.eventspherebackend.model.StudentEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface StudentEventRepository extends JpaRepository<StudentEvent, String> {

    List<StudentEvent> findByStudentUsernameAndEventEventDate(String username,Date eventDate);

    List<StudentEvent> findStudentByEventId(int id);

    void deleteByEventIdAndStudentId(int eventId, int studentId);
}
