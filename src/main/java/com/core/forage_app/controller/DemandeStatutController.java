package com.core.forage_app.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.forage_app.entity.Demande;
import com.core.forage_app.entity.DemandeStatut;
import com.core.forage_app.service.DemandeService;
import com.core.forage_app.service.StatutService;

@Controller
public class DemandeStatutController {
    @Autowired
    DemandeService demandeService;
    @Autowired
    StatutService statutService;

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
}
