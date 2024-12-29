package com.example.eventspherebackend.repository;

import com.example.eventspherebackend.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<Portfolio, String> {
    // Add custom query methods if required
}
