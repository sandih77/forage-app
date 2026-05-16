package com.core.forage_app.service;

import java.time.LocalDateTime;
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

    @Transactional // 👈 S'assure que TOUT passe ou TOUT casse en bloc
    public void enregistrerDevisComplet(DevisForm form) {
        
        // 1. Création et enregistrement de l'entité parente Devis
        Devis devis = new Devis();
        devis.setDemande(form.getDemande());
        devis.setTypeDevis(form.getTypeDevis());
        devis.setDateDevis(LocalDateTime.now());
        
        // On sauvegarde le devis. Grâce à l'ID auto-généré, l'objet 'devis' aura son nouvel ID mis à jour.
        devis = devisRepository.save(devis);

        // 2. Parcourir les lignes du formulaire pour créer les détails associés
        for (DevisForm.DetailDevisForm ligne : form.getLignes()) {
            if (ligne.getDesignation() != null && !ligne.getDesignation().trim().isEmpty()) {
                DetailDevis detail = new DetailDevis();
                detail.setDevis(devis); // 👈 Attribution de la clé étrangère du devis parent tout juste créé !
                detail.setDesignation(ligne.getDesignation());
                detail.setDescription(ligne.getDescription());
                detail.setQuantity(ligne.getQuantite());
                detail.setPrixUnitaire(ligne.getPrixUnitaire());
                
                // Sauvegarde de la ligne de détail
                detailDevisRepository.save(detail);
            }
        }
    }
}