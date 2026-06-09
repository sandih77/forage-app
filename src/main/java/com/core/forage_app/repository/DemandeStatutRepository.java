package com.core.forage_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.core.forage_app.entity.DemandeStatut;

public interface DemandeStatutRepository extends JpaRepository<DemandeStatut, Integer> {
    public DemandeStatut findByDemandeId(int demandeId);

    public DemandeStatut findTopByDemandeIdOrderByIdDesc(int demandeId);

    public List<DemandeStatut> findByDemandeIdOrderByIdAsc(int demandeId);

    public List<DemandeStatut> findByDemandeIdOrderByDateStatutAsc(int demandeId);
    public DemandeStatut findTopByDemandeIdOrderByDateStatutDesc(int idDemande);
}
