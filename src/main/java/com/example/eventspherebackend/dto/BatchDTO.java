package com.example.eventspherebackend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class BatchDTO {

    private int id;
    private String name;
    private int consultantId;
    private String consultantName;
    private Date startDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String status;

}
