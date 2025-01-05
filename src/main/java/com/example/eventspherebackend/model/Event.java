package com.example.eventspherebackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String type;

    private String timePeriod;
    private String sessionLink;
    private String platform;

    public void setId(String id) {
        this.id = Integer.parseInt(id);
    }

    @ManyToOne
    @JoinColumn(name = "coordinator_id")
    private Users coordinator;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;
    private String status = "planned";
    private Date eventDate;

//    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<Attendance> attendances = new HashSet<>();
//
//    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<StudentEvent> studentEvents = new HashSet<>();
//
//    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<BatchEvent> batchEvents = new HashSet<>();
}
