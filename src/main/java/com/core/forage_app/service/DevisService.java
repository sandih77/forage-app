package com.core.forage_app.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core.forage_app.dto.DevisForm;
import com.core.forage_app.entity.Devis;
import com.core.forage_app.entity.DetailDevis;
import com.core.forage_app.repository.DevisRepository;       
import com.core.forage_app.repository.DetailDevisRepository;

@Service
public class DevisService {

    @Autowired
    private DevisRepository devisRepository;

    @Autowired
    private DetailDevisRepository detailDevisRepository;

    @Transactional 
    public void enregistrerDevisComplet(DevisForm form) {
        
        Devis devis = new Devis();
        devis.setDemande(form.getDemande());
        devis.setTypeDevis(form.getTypeDevis());
        devis.setDateDevis(LocalDateTime.now());
        
        devis = devisRepository.save(devis);

        for (DevisForm.DetailDevisForm ligne : form.getLignes()) {
            if (ligne.getDesignation() != null && !ligne.getDesignation().trim().isEmpty()) {
                DetailDevis detail = new DetailDevis();
                detail.setDevis(devis); 
                detail.setDesignation(ligne.getDesignation());
                detail.setDescription(ligne.getDescription());
                detail.setQuantity(ligne.getQuantite());
                detail.setPrixUnitaire(ligne.getPrixUnitaire());
                
                detailDevisRepository.save(detail);
            }
        }
    }

    public List<Devis> findAll() {
        return this.devisRepository.findAll();
    }

    public Devis findById(int id) {
        return this.devisRepository.findById(id).orElse(null);
    }

    public void delete(Devis devis) {
        this.devisRepository.delete(devis);
    }
}