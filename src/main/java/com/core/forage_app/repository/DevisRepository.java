package com.core.forage_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.core.forage_app.entity.Devis;

public interface DevisRepository extends JpaRepository<Devis, Integer> {
    @Query("SELECT d FROM Devis d LEFT JOIN FETCH d.listDetailDevis WHERE d.id = :id")
    public Devis findByIdWithDetails(@Param("id") int id);
}
