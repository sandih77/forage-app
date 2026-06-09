package com.core.forage_app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.core.forage_app.dto.DemandeDTO;
import com.core.forage_app.service.AlerteService;
import com.core.forage_app.service.DemandeService;

@CrossOrigin(origins = "*")
@RestController
public class AlerteController {

    @Autowired
    private AlerteService alerteService;

    @Autowired
    private DemandeService demandeService;

    @GetMapping("/demande/{id}/alertes")
    public List<Map<String, Object>> voirDetailsDemande(@PathVariable("id") int id) {
        return alerteService.getAlertesDetailsParDemande(id);
    }

    @GetMapping("/demande/api/listAll")
    public List<DemandeDTO> listAll() {
        return demandeService.findAllWithStatus()
                .stream()
                .map(DemandeDTO::new)
                .toList();
    }
}