package com.core.forage_app.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.core.forage_app.entity.Demande;
import com.core.forage_app.entity.DemandeStatut;
import com.core.forage_app.entity.Statut;
import com.core.forage_app.service.ClientService;
import com.core.forage_app.service.CommuneService;
import com.core.forage_app.service.DemandeService;
import com.core.forage_app.service.DemandeStatutService;
import com.core.forage_app.service.StatutService;

@Controller
public class DemandeController {
    @Autowired
    DemandeService demandeService;
    @Autowired
    ClientService clientService;
    @Autowired
    CommuneService communeService;
    @Autowired
    DemandeStatutService demandeStatutService;
    @Autowired
    StatutService statutService;

    @GetMapping("/demande/list")
    public String list(Model model) {
        model.addAttribute("demandes", this.demandeService.findAll());
        model.addAttribute("communes", communeService.findAll());
        return "demande/list";
    }

    @GetMapping("/demande/showForm")
    public String showForm(Model model) {
        model.addAttribute("demande", new Demande());
        model.addAttribute("clients", this.clientService.findAll());
        model.addAttribute("communes", this.communeService.findAll());
        return "demande/form";
    }

    @PostMapping("/demande/save")
    public String save(@ModelAttribute Demande demande) {
        this.demandeService.save(demande);
        DemandeStatut ds = this.demandeStatutService.findByDemandeId(demande.getId());
        if (ds == null) {
            DemandeStatut dstoSave = new DemandeStatut();
            Demande d = this.demandeService.findById(demande.getId());
            Statut s = this.statutService.findById(1);
            dstoSave.setDateStatut(LocalDateTime.now());
            dstoSave.setDemande(d);
            dstoSave.setStatut(s);
            this.demandeStatutService.save(dstoSave);
        }
        return "redirect:/demande/list";
    }

    @GetMapping("/demande/search")
    public String list(@RequestParam(required = false) String client,
            @RequestParam(required = false) Integer communeId,
            @RequestParam(required = false) String lieu,
            Model model) {

        List<Demande> demandes = demandeService.findFiltered(client, communeId, lieu);

        model.addAttribute("demandes", demandes);

        return "demande/list";
    }

    @GetMapping("/demande/edit/{id}")
    public String showFormEdit(@PathVariable("id") int id, Model model) {

        Demande demande = this.demandeService.findById(id);

        model.addAttribute("demande", demande);
        model.addAttribute("clients", this.clientService.findAll());
        model.addAttribute("communes", this.communeService.findAll());

        return "demande/form";
    }

    @PostMapping("/demande/edit")
    public String edit(@ModelAttribute Demande demande) {
        Demande demandeExistante = this.demandeService.findById(demande.getId());

        if (demandeExistante != null) {
            demandeExistante.setLieu(demande.getLieu());
            demandeExistante.setReference(demande.getReference());
            demandeExistante.setClient(demande.getClient());
            demandeExistante.setCommune(demande.getCommune());
            this.demandeService.save(demandeExistante);
        }

        return "redirect:/demande/list";
    }

    @GetMapping("/demande/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        Demande demande = this.demandeService.findById(id);
        this.demandeService.delete(demande);
        return "redirect:/demande/list";
    }
}
