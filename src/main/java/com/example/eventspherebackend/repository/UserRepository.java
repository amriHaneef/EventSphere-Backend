package com.example.eventspherebackend.repository;

import com.example.eventspherebackend.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users, String> {
    Users findByUsername(String username);

    boolean existsByUsername(String username);

    List<Users> findByRole(String role);
}
