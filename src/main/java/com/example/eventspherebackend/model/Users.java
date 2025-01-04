package com.example.eventspherebackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Users {

    public Users(String username, String password, String role, String name, String email, Date dob, Integer age) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.age = age;
    }

    // Constructors
    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Users( String username, String password, String role,String name) {

        this.username = username;
        this.password = password;
        this.role = role;
        this.name =name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username = "Default Name";

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private String name;

    private String email;
    private Date dob;
    private Integer age;
    private boolean state = true;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

    private String status = "active";

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<StudentBatch> studentBatches = new HashSet<>();
}
