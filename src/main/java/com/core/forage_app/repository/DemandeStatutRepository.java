package com.core.forage_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.core.forage_app.entity.DemandeStatut;

public interface DemandeStatutRepository extends JpaRepository<DemandeStatut, Integer> {
    public DemandeStatut findByDemandeId(int demandeId);  
}
