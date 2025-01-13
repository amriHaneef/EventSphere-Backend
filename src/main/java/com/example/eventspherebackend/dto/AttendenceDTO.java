package com.example.eventspherebackend.dto;

import com.example.eventspherebackend.model.Users;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AttendenceDTO {
    private int Id;
    private int eventId;
    private int studentId;
    private float marks;
    private String attendanceStatus;
}
