package com.core.forage_app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.forage_app.entity.Region;
import com.core.forage_app.repository.RegionRepository;

@Service
public class RegionService {
    @Autowired
    RegionRepository regionRepository;

    public List<Region> findAll() {
        return this.regionRepository.findAll();
    }

    public void save(Region region) {
        this.regionRepository.save(region);
    }

    public void delete(Region region) {
        this.regionRepository.delete(region);
    }
}
