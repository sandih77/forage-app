package com.core.forage_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.core.forage_app.entity.Commune;

public interface CommuneRepository extends JpaRepository<Commune, Integer> {
    
}
