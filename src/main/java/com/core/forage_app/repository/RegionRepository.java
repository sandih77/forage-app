package com.core.forage_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.core.forage_app.entity.Region;

public interface RegionRepository extends JpaRepository<Region, Integer> {
    
}
