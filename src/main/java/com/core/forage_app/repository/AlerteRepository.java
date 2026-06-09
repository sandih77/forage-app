package com.core.forage_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.core.forage_app.entity.Alerte;

public interface AlerteRepository extends JpaRepository<Alerte, Integer> {
    public List<Alerte> findByStatut1IdAndStatut2Id(int statut1Id, int statut2Id);

    public List<Alerte> findByStatut1IdLessThanEqualAndStatut2IdGreaterThanEqual(int statutActuel, int statutSuivant);
}
