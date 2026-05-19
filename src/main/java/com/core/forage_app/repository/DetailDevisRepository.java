package com.core.forage_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.core.forage_app.entity.DetailDevis;

public interface DetailDevisRepository extends JpaRepository<DetailDevis, Integer> {
    @Modifying
    @Query("DELETE FROM DetailDevis d WHERE d.devis.id = :id")
    public void deleteByDevisId(@Param("id") int id);
}
