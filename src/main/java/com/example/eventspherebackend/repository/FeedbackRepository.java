package com.example.eventspherebackend.repository;

import com.example.eventspherebackend.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, String> {
    // Add custom query methods if required
}
