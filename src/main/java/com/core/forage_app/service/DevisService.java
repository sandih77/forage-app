package com.core.forage_app.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public Devis findByIdWithDetails(int id) {
        return this.devisRepository.findByIdWithDetails(id);
    }

    @Transactional
    public void mettreAJourDevisComplet(Devis devisExistant, DevisForm form) {

        devisExistant.setTypeDevis(form.getTypeDevis());
        devisExistant.setDateDevis(LocalDateTime.now());
        devisRepository.save(devisExistant);

        detailDevisRepository.deleteByDevisId(devisExistant.getId());

        List<DetailDevis> nouveaux = new ArrayList<>();

        for (var ligne : form.getLignes()) {
            if (ligne.getDesignation() != null && !ligne.getDesignation().isBlank()) {

                DetailDevis d = new DetailDevis();
                d.setDevis(devisExistant);
                d.setDesignation(ligne.getDesignation());
                d.setDescription(ligne.getDescription());
                d.setQuantity(ligne.getQuantite());
                d.setPrixUnitaire(ligne.getPrixUnitaire());

                nouveaux.add(d);
            }
        }

        detailDevisRepository.saveAll(nouveaux);
    }

    public void delete(Devis devis) {
        this.devisRepository.delete(devis);
    }
}