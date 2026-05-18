package com.core.forage_app.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.forage_app.dto.DevisForm;
import com.core.forage_app.entity.Demande;
import com.core.forage_app.entity.DemandeStatut;
import com.core.forage_app.entity.TypeDevis;
import com.core.forage_app.service.DemandeService;
import com.core.forage_app.service.DemandeStatutService;
import com.core.forage_app.service.DevisService;
import com.core.forage_app.service.StatutService;
import com.core.forage_app.service.TypeDevisService;

@Controller
public class DevisController {
    @Autowired
    private DemandeService demandeService;
    @Autowired
    private DemandeStatutService demandeStatutService;
    @Autowired
    private TypeDevisService typeDevisService;
    @Autowired
    private DevisService devisService;
    @Autowired
    private StatutService statutService;

    @GetMapping("/devis/showForm")
    public String showForm() {
        return "devis/form";
    }

    @GetMapping("/devis/findDemande")
    @ResponseBody
    public Map<String, Object> findDemande(@RequestParam("reference") String reference) {
        Map<String, Object> response = new HashMap<>();

        if (reference == null || reference.trim().isEmpty()) {
            return response;
        }

        Demande demande = demandeService.findByReference(reference);

        if (demande != null) {
            response.put("found", true);
            response.put("lieu", demande.getLieu() != null ? demande.getLieu() : "Aucun");
            response.put("reference", demande.getReference() != null ? demande.getReference() : "Aucune");
            response.put("clientNom", demande.getClient() != null ? demande.getClient().getNom() : "Aucun");
            response.put("communeNom", demande.getCommune() != null ? demande.getCommune().getNom() : "Aucune");

            DemandeStatut demandeStatutActuelle = demandeStatutService.findTopByDemandeIdOrderByIdDesc(demande.getId());
            if (demandeStatutActuelle != null && demandeStatutActuelle.getStatut() != null
                    && demandeStatutActuelle.getStatut().getId() == 1) {
                TypeDevis td = this.typeDevisService.findByType("Etude");
                List<TypeDevis> listTd = new ArrayList<>();
                listTd.add(td);
                List<Map<String, Object>> typeDevisList = new ArrayList<>();

                for (TypeDevis t : listTd) {
                    Map<String, Object> tdMap = new HashMap<>();
                    tdMap.put("id", t.getId());
                    tdMap.put("nom", t.getType());

                    typeDevisList.add(tdMap);
                }

                response.put("id", demande.getId());
                response.put("hasTypeDevis", true);
                response.put("typeDevis", typeDevisList);

            } else {
                List<TypeDevis> listTd = this.typeDevisService.findAll();
                List<Map<String, Object>> typeDevisList = new ArrayList<>();

                for (TypeDevis t : listTd) {
                    Map<String, Object> tdMap = new HashMap<>();
                    tdMap.put("id", t.getId());
                    tdMap.put("nom", t.getType());

                    typeDevisList.add(tdMap);
                }

                response.put("id", demande.getId());
                response.put("hasTypeDevis", true);
                response.put("typeDevis", typeDevisList);
            }
        } else {
            response.put("found", false);
        }

        return response;
    }

    @PostMapping("/devis/save")
    public String saveDevis(@ModelAttribute DevisForm devisForm) {
        DemandeStatut demandeStatut = this.demandeStatutService.findByDemandeId(devisForm.getDemande().getId());

        if (demandeStatut.getStatut().getId() == 1 && demandeStatut != null) {
            DemandeStatut newDemandeStatut = new DemandeStatut();
            newDemandeStatut.setDateStatut(LocalDateTime.now());
            newDemandeStatut.setDemande(devisForm.getDemande());
            newDemandeStatut.setStatut(this.statutService.findById(2));
            this.demandeStatutService.save(newDemandeStatut);
        }

        if (devisForm.getDemande() == null || devisForm.getLignes().isEmpty()) {
            return "redirect:/devis/showForm?error=donnees_invalides";
        }

        try {
            this.devisService.enregistrerDevisComplet(devisForm);
        } catch (Exception e) {
            return "redirect:/devis/showForm?error=erreur_sauvegarde";
        }

        return "redirect:/devis/showForm?success=enregistre";
    }

    @GetMapping("/devis/list")
    public String findAll(Model model) {
        model.addAttribute("devis", this.devisService.findAll());
        return "devis/list";
    }
}
