package com.core.forage_app.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "devis")
public class Devis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_demande")
    private Demande demande;

    private LocalDateTime dateDevis;

    @ManyToOne
    @JoinColumn(name = "id_typeDevis")
    private TypeDevis typeDevis;

    public TypeDevis getTypeDevis() {
        return typeDevis;
    }

    public void setTypeDevis(TypeDevis typeDevis) {
        this.typeDevis = typeDevis;
    }

    @OneToMany(mappedBy = "devis", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<DetailDevis> listDetailDevis = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Demande getDemande() {
        return demande;
    }

    public void setDemande(Demande demande) {
        this.demande = demande;
    }

    public LocalDateTime getDateDevis() {
        return dateDevis;
    }

    public void setDateDevis(LocalDateTime dateDevis) {
        this.dateDevis = dateDevis;
    }

    public List<DetailDevis> getListDetailDevis() {
        return listDetailDevis;
    }

    public void setListDetailDevis(List<DetailDevis> listDetailDevis) {
        this.listDetailDevis = listDetailDevis;
    }
}
