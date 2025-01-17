package com.example.eventspherebackend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EventStudentDTO {
    private List<String> studentIds;
    private String eventId;
}
