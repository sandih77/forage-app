package com.core.forage_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.core.forage_app.entity.Demande;

public interface DemandeRepository extends JpaRepository<Demande, Integer> {
    public Demande findByReference(String reference);

    public Demande findTopByReferenceOrderByIdDesc(String reference);

    @Query("SELECT DISTINCT d FROM Demande d LEFT JOIN FETCH d.listDemandeStatut")
    List<Demande> findAllWithStatuts();
}
