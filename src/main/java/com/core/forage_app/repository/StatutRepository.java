package com.core.forage_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.core.forage_app.entity.Statut;

public interface StatutRepository extends JpaRepository<Statut, Integer> {
    
}
