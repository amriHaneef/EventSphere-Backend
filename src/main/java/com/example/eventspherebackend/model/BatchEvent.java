package com.example.eventspherebackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "batch_events")
public class BatchEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;

    @ManyToOne
    @JoinColumn(name = "batch_id", nullable = false)
    private Batch batch;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public void setId(String id) {
        this.id = Integer.parseInt(id);
    }

    public String getId() {
        return String.valueOf(id);
    }


}
