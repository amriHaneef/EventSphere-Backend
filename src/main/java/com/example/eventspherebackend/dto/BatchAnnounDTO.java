package com.example.eventspherebackend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BatchAnnounDTO {
    private List<String> batchIds;
    private String announcementId;

}
