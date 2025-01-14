package com.example.eventspherebackend.repository;

import com.example.eventspherebackend.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, String> {
    List<Attendance> findByEventId(int i);
}
