package com.example.eventspherebackend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PortfolioDTO {
    private int id;
    private int studentId;
    private String studentName; // To include student details
    private String achievements;
    private String projects;
    private String skills;
    private String certifications;
    private Float gpa;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
