package com.example.eventspherebackend.repository;


import com.example.eventspherebackend.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, String> {
    // Add custom query methods if required
}
