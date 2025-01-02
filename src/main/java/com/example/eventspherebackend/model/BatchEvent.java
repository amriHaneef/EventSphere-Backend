package com.example.eventspherebackend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "batch_events")
public class BatchEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "batch_id", nullable = false)
    private Batch batch;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public void setId(String id) {
        this.id = id;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public Batch getBatch() {
        return batch;
    }

    public Event getEvent() {
        return event;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
