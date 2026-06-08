package com.core.forage_app.dto;

import com.core.forage_app.entity.Demande;

public class DemandeDTO {
    private int id;
    private String clientNom;
    private String lieu;
    private String reference;
    private String communeNom;
    private String districtNom;
    private String regionNom;

    // constructeur
    public DemandeDTO(Demande d) {
        this.id = d.getId();
        this.lieu = d.getLieu();
        this.reference = d.getReference();

        // CLIENT
        if (d.getClient() != null) {
            this.clientNom = d.getClient().getNom();
        }

        // COMMUNE / DISTRICT / REGION
        if (d.getCommune() != null) {
            this.communeNom = d.getCommune().getNom();

            if (d.getCommune().getDistrict() != null) {
                this.districtNom = d.getCommune().getDistrict().getNom();

                if (d.getCommune().getDistrict().getRegion() != null) {
                    this.regionNom = d.getCommune().getDistrict().getRegion().getNom();
                }
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getCommuneNom() {
        return communeNom;
    }

    public void setCommuneNom(String communeNom) {
        this.communeNom = communeNom;
    }

    public String getDistrictNom() {
        return districtNom;
    }

    public void setDistrictNom(String districtNom) {
        this.districtNom = districtNom;
    }

    public String getRegionNom() {
        return regionNom;
    }

    public void setRegionNom(String regionNom) {
        this.regionNom = regionNom;
    }

    public String getClientNom() {
        return clientNom;
    }

    public void setClientNom(String clientNom) {
        this.clientNom = clientNom;
    }

    // getters seulement si nécessaire
}
