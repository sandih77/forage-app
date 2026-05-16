package com.core.forage_app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.forage_app.entity.Commune;
import com.core.forage_app.repository.CommuneRepository;

@Service
public class CommuneService {
    @Autowired
    CommuneRepository communeRepository;

    public List<Commune> findAll() {
        return this.communeRepository.findAll();
    }

    public void save(Commune commune) {
        this.communeRepository.save(commune);
    }

    public void delete(Commune commune) {
        this.communeRepository.delete(commune);
    }

    public Commune findById(int id) {
        return this.communeRepository.findById(id).orElse(null);
    }
}
