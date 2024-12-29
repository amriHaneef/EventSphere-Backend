package com.example.eventspherebackend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String type;

    @ManyToOne
    @JoinColumn(name = "batch_id")
    private Batch batch;

    private String timePeriod;
    private String sessionLink;
    private String platform;

    @ManyToOne
    @JoinColumn(name = "coordinator_id")
    private Users coordinator;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;
    private String status = "planned";

    // Getters and Setters
}
