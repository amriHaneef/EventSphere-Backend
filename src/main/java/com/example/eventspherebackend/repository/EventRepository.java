package com.example.eventspherebackend.repository;

import com.example.eventspherebackend.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, String> {

    List<Event> findByEventDate(Date eventDate);

    List<Event> findByCoordinatorUsernameAndEventDate(String username,Date eventDate);



}
