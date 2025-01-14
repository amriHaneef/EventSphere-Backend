package com.example.eventspherebackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedbackDTO {

    private String feedbackId;
    private String feedback;
    private int eventId;
    private String targetType;
}
