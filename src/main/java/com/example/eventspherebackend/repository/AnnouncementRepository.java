package com.example.eventspherebackend.repository;


import com.example.eventspherebackend.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, String> {
    List<Announcement> findByCreatedBy(String username);


}
