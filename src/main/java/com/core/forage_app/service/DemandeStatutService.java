package com.core.forage_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.forage_app.entity.DemandeStatut;
import com.core.forage_app.repository.DemandeStatutRepository;

@Service
public class DemandeStatutService {
    @Autowired
    private DemandeStatutRepository demandeStatutRepository;

    public DemandeStatut findByDemandeId(int id) {
        return this.demandeStatutRepository.findByDemandeId(id);
    }

    public DemandeStatut findTopByDemandeIdOrderByIdDesc(int id) {
        return this.demandeStatutRepository.findTopByDemandeIdOrderByIdDesc(id);
    }

    public void save(DemandeStatut demandeStatut) {
        this.demandeStatutRepository.save(demandeStatut);
    }
}
