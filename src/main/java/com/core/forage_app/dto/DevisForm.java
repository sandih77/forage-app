package com.core.forage_app.dto;

import java.util.ArrayList;
import java.util.List;
import com.core.forage_app.entity.Demande;
import com.core.forage_app.entity.TypeDevis;

public class DevisForm {
    private Demande demande;
    private TypeDevis typeDevis;
    private List<DetailDevisForm> lignes = new ArrayList<>();

    // Getters et Setters
    public Demande getDemande() {
        return demande;
    }

    public void setDemande(Demande demande) {
        this.demande = demande;
    }

    public TypeDevis getTypeDevis() {
        return typeDevis;
    }

    public void setTypeDevis(TypeDevis typeDevis) {
        this.typeDevis = typeDevis;
    }

    public List<DetailDevisForm> getLignes() {
        return lignes;
    }

    public void setLignes(List<DetailDevisForm> lignes) {
        this.lignes = lignes;
    }

    // Sous-classe pour mapper chaque ligne de formulaire dynamiquement
    public static class DetailDevisForm {
        private String designation;
        private String description;
        private int quantite;
        private float prixUnitaire;

        // Getters et Setters
        public String getDesignation() {
            return designation;
        }

        public void setDesignation(String designation) {
            this.designation = designation;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getQuantite() {
            return quantite;
        }

        public void setQuantite(int quantite) {
            this.quantite = quantite;
        }

        public float getPrixUnitaire() {
            return prixUnitaire;
        }

        public void setPrixUnitaire(float prixUnitaire) {
            this.prixUnitaire = prixUnitaire;
        }
    }
}