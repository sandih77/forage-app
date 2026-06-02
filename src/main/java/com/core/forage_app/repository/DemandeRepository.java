package com.core.forage_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.core.forage_app.entity.Demande;

public interface DemandeRepository extends JpaRepository<Demande, Integer> {
    public Demande findByReference(String reference);
    public Demande findTopByReferenceOrderByIdDesc(String reference);
}
