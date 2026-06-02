package com.core.forage_app.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.forage_app.entity.Demande;
import com.core.forage_app.entity.DemandeStatut;
import com.core.forage_app.service.DemandeService;
import com.core.forage_app.service.DemandeStatutService;
import com.core.forage_app.service.StatutService;

@Controller
public class DemandeStatutController {
    @Autowired
    DemandeService demandeService;
    @Autowired
    StatutService statutService;
    @Autowired
    DemandeStatutService demandeStatutService;

    @GetMapping("/demandeStatut/findDemande")
    @ResponseBody
    public Map<String, Object> findDemande(@RequestParam("reference") String reference) {
        Map<String, Object> response = new HashMap<>();

        if (reference == null || reference.trim().isEmpty()) {
            return response;
        }

        Demande demande = demandeService.findByReference(reference);

        if (demande != null) {
            response.put("found", true);
            response.put("id", demande.getId());
            response.put("reference", demande.getReference());
        } else {
            response.put("found", false);
        }

        return response;
    }

    @GetMapping("demandeStatut/showForm")
    public String showForm(Model model) {
        model.addAttribute("demandeStatut", new DemandeStatut());

        model.addAttribute("statuts",
                statutService.findAll());

        return "demandeStatut/form";
    }

    @PostMapping("demandeStatut/save")
    public String save(@ModelAttribute DemandeStatut demandeStatut) {
        DemandeStatut lastDTS = this.demandeStatutService.findTopByDemandeIdOrderByIdDesc(demandeStatut.getDemande().getId());
        if (lastDTS != null) {
            float dt = this.demandeStatutService.calculateDT(lastDTS.getDateStatut(), demandeStatut.getDateStatut());
            demandeStatut.setDureeTravail(dt);
            this.demandeStatutService.save(demandeStatut);
            return "redirect:/demandeStatut/list";
        } else {
            demandeStatut.setDureeTravail(0.0f);
            this.demandeStatutService.save(demandeStatut);
        }
        return "redirect:/demandeStatut/list";
    }

    @GetMapping("demandeStatut/list")
    public String list(Model model){
        model.addAttribute("demandeStatuts", this.demandeStatutService.findAll());
        return "demandeStatut/list";
    }
}
