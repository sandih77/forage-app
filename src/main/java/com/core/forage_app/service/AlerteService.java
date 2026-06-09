package com.core.forage_app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
        Map<String, Map<String, Object>> alertesFiltrees = new LinkedHashMap<>();

        List<DemandeStatut> historique = demandeStatutRepository.findByDemandeIdOrderByIdAsc(demandeId);
        List<Alerte> alertes = alerteRepository.findAll();

        for (Alerte regle : alertes) {
            long dureeTotale = 0;
            boolean debutTrouve = false;

            for (DemandeStatut ds : historique) {
                int statutId = ds.getStatut().getId();

                if (statutId == regle.getStatut1().getId()) {
                    debutTrouve = true;
                }

                if (debutTrouve
                        && statutId > regle.getStatut1().getId()
                        && statutId <= regle.getStatut2().getId()) {
                    dureeTotale += ds.getDureeTravail();
                }

                if (statutId == regle.getStatut2().getId()) {
                    break;
                }
            }

            if (dureeTotale >= regle.getDureeTravail1() && dureeTotale <= regle.getDureeTravail2()) {
                
                String transitionKey = regle.getStatut1().getId() + "->" + regle.getStatut2().getId();
                
                long depassement = (long) (dureeTotale - regle.getDureeTravail1());

                alertesFiltrees.put(transitionKey, creerAlerteMap(regle, dureeTotale, depassement));
            }
        }

        return new ArrayList<>(alertesFiltrees.values());
    }

    private Map<String, Object> creerAlerteMap(Alerte regle, long dureeReelle, long depassement) {
        Map<String, Object> alerteMap = new HashMap<>();
        alerteMap.put("couleur", regle.getCouleur());
        alerteMap.put("transition", regle.getStatut1().getLibelle() + " -> " + regle.getStatut2().getLibelle());
        
        alerteMap.put("dureeLimiteMin", regle.getDureeTravail1());
        alerteMap.put("dureeLimiteMax", regle.getDureeTravail2());
        alerteMap.put("dureeLimite", regle.getDureeTravail1() + " - " + regle.getDureeTravail2()); 
        
        alerteMap.put("dureeReelle", dureeReelle);
        alerteMap.put("depassement", depassement);
        return alerteMap;
    }
}