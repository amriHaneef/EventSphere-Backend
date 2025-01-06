package com.example.eventspherebackend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class StudentAnnounDTO {
    private List<String> studentIds;
    private String announcementId;
}
