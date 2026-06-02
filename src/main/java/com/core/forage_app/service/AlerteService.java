package com.core.forage_app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.forage_app.entity.Alerte;
import com.core.forage_app.entity.DemandeStatut;
import com.core.forage_app.repository.AlerteRepository;
import com.core.forage_app.repository.DemandeStatutRepository;

@Service
public class AlerteService {
    @Autowired
    AlerteRepository alerteRepository;
    @Autowired
    DemandeStatutRepository demandeStatutRepository;

    public List<Alerte> findAll() {
        return this.alerteRepository.findAll();
    }
    
    public List<Map<String, Object>> getAlertesDetailsParDemande(int demandeId) {
        List<Map<String, Object>> resultats = new ArrayList<>();
        List<DemandeStatut> historique = demandeStatutRepository.findByDemandeIdOrderByIdAsc(demandeId);

        if (historique.size() < 2)
            return resultats;

        for (int i = 0; i < historique.size() - 1; i++) {
            DemandeStatut actuel = historique.get(i);
            DemandeStatut suivant = historique.get(i + 1);

            Alerte regle = alerteRepository.findByStatut1IdAndStatut2Id(
                    actuel.getStatut().getId(), suivant.getStatut().getId());

            if (regle != null && suivant.getDureeTravail() > regle.getDureeTravail()) {
                Map<String, Object> alerteMap = new HashMap<>();
                alerteMap.put("couleur", regle.getCouleur());
                alerteMap.put("transition",
                        actuel.getStatut().getLibelle() + " -> " + suivant.getStatut().getLibelle());
                alerteMap.put("dureeLimite", regle.getDureeTravail());
                alerteMap.put("dureeReelle", suivant.getDureeTravail());
                alerteMap.put("depassement", suivant.getDureeTravail() - regle.getDureeTravail());
                resultats.add(alerteMap);
            }
        }
        return resultats;
    }
}
