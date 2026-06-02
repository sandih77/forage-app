package com.core.forage_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.core.forage_app.entity.Alerte;

public interface AlerteRepository extends JpaRepository<Alerte, Integer> {
    public Alerte findByStatut1IdAndStatut2Id(int statut1Id, int statut2Id);
}
