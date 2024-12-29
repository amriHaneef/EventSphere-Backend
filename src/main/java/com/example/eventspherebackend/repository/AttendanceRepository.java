package com.example.eventspherebackend.repository;

import com.example.eventspherebackend.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, String> {
    // Add custom query methods if required
}
