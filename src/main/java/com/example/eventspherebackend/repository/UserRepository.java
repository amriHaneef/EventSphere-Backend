package com.example.eventspherebackend.repository;

import com.example.eventspherebackend.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, String> {
    Users findByUsername(String username);

    boolean existsByUsername(String username);
}
