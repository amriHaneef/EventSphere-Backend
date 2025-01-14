package com.example.eventspherebackend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
public class UsersDTO {

    private int id;
    private String username;
    private String password;
    private String role;
    private String name;
    private String email;
    private Date dob;
    private Integer age;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
