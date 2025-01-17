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
        setRole(role); // Use the role validation method
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.age = age;
    }

    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Users(String username, String password, String role, String name) {
        this.username = username;
        this.password = password;
        setRole(role); // Use the role validation method
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    // Getter for role
    @Getter
    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private String name;

    private String email;

    private Date dob;

    private Integer age;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

    private String status = "active";

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<StudentBatch> studentBatches = new HashSet<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<StudentEvent> studentEvents = new HashSet<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<StudentAnnoun> studentAnnouncements = new HashSet<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Portfolio> portfolios = new HashSet<>();

    // Role validation method
    private void validateRole(String role) {
        if (!role.equalsIgnoreCase("ADMIN") &&
                !role.equalsIgnoreCase("TEACHER") &&
                !role.equalsIgnoreCase("STUDENT")) {
            throw new IllegalArgumentException("Invalid role: " + role + ". Role must be ADMIN, TEACHER, or STUDENT.");
        }
    }

    // Setter with role validation
    public void setRole(String role) {
        validateRole(role);
        this.role = role.toUpperCase(); // Normalize to uppercase
    }

}
