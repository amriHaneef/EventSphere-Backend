package com.example.eventspherebackend.repository;

import com.example.eventspherebackend.dto.EventDTO;
import com.example.eventspherebackend.model.Event;
import com.example.eventspherebackend.model.StudentEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentEventRepository extends JpaRepository<StudentEvent, String> {
    // Add custom query methods if required, e.g.:
     List<Event> findEventByUsername(String username);
}
