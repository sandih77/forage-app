package com.core.forage_app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.forage_app.entity.Demande;
import com.core.forage_app.repository.DemandeRepository;

@Service
public class DemandeService {
    @Autowired
    DemandeRepository demandeRepository;

    public List<Demande> findAll() {
        return this.demandeRepository.findAll();
    }

    public void save(Demande demande) {
        this.demandeRepository.save(demande);
    }

    public void delete(Demande demande) {
        this.demandeRepository.delete(demande);
    }

    public List<Demande> findFiltered(String client, Integer communeId, String lieu) {

        return this.demandeRepository.findAll().stream()
                .filter(d -> client == null || client.isEmpty()
                        || (d.getClient() != null
                                && d.getClient().getNom().toLowerCase().contains(client.toLowerCase())))
                .filter(d -> communeId == null
                        || (d.getCommune() != null
                                && d.getCommune().getId() == communeId))
                .filter(d -> lieu == null || lieu.isEmpty()
                        || d.getLieu().toLowerCase().contains(lieu.toLowerCase()))
                .toList();
    }

    public Demande findById(int id) {
        return this.demandeRepository.findById(id).orElse(null);
    }

    public Demande findByReference(String reference) {
        return this.demandeRepository.findByReference(reference) != null ? this.demandeRepository.findByReference(reference) : new Demande();
    }
}
