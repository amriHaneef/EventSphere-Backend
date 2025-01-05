package com.example.eventspherebackend.model;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "students_batches")
public class StudentBatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Users student;

    @ManyToOne
    @JoinColumn(name = "batch_id", nullable = false)
    private Batch batch;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public String getId() {
        return String.valueOf(id);
    }

    public Users getStudent() {
        return student;
    }

    public Batch getBatch() {
        return batch;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
