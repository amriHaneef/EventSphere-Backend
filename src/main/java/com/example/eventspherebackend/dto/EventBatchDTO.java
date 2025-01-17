package com.example.eventspherebackend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EventBatchDTO {
    private List<String> batchIds;
    private String eventId;
}
