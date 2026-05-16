package com.core.forage_app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.forage_app.entity.District;
import com.core.forage_app.repository.DistrictRepository;

@Service
public class DistrictService {
    @Autowired
    DistrictRepository districtRepository;

    public List<District> findAll() {
        return this.districtRepository.findAll();
    }

    public void save(District district) {
        this.districtRepository.save(district);
    }

    public void delete(District district) {
        this.districtRepository.delete(district);
    }
}
