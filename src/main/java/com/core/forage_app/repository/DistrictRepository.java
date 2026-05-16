package com.core.forage_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.core.forage_app.entity.District;

public interface DistrictRepository extends JpaRepository<District, Integer> {
    
}
