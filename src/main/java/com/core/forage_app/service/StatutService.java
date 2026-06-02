package com.core.forage_app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.forage_app.entity.Statut;
import com.core.forage_app.repository.StatutRepository;

@Service
public class StatutService {
    @Autowired
    private StatutRepository statutRepository;

    public void save(Statut statut) {
        this.statutRepository.save(statut);
    }

    public Statut findById(int id) {
        return this.statutRepository.findById(id).orElse(null);
    }

    public List<Statut> findAll() {
        return this.statutRepository.findAll();
    }
}
