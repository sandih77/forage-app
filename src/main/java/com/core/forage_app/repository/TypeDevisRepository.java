package com.core.forage_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.core.forage_app.entity.TypeDevis;

public interface TypeDevisRepository extends JpaRepository<TypeDevis, Integer> {
    public TypeDevis findByType(String type);   
}
