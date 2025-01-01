package com.example.eventspherebackend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String type;

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
    private Date eventDate;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getTimePeriod() {
        return timePeriod;
    }

    public String getSessionLink() {
        return sessionLink;
    }

    public String getPlatform() {
        return platform;
    }

    public Users getCoordinator() {
        return coordinator;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getStatus() {
        return status;
    }

    public Date getEventDate() {
        return eventDate;
    }
}
