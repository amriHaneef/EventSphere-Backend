package com.example.eventspherebackend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class AnnouncementDTO {
    private int id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String createdBy;
    private String role;
    private List<Integer> studentIds; // List of associated student IDs
    private List<Integer> batchIds;   // List of associated batch IDs


}
