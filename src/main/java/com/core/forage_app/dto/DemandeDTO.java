package com.core.forage_app.dto;

import java.util.List;
import com.core.forage_app.entity.Demande;
import com.core.forage_app.entity.DemandeStatut;

public class DemandeDTO {
    private int id;
    private String clientNom;
    private String lieu;
    private String reference;
    private String communeNom;
    private String districtNom;
    private String regionNom;
    private String dureeTotaleTravail;
    private boolean travailTermine;

    public DemandeDTO(Demande d) {
        this.id = d.getId();
        this.lieu = d.getLieu();
        this.reference = d.getReference();

        if (d.getClient() != null) {
            this.clientNom = d.getClient().getNom();
        }

        if (d.getCommune() != null) {
            this.communeNom = d.getCommune().getNom();
            if (d.getCommune().getDistrict() != null) {
                this.districtNom = d.getCommune().getDistrict().getNom();
                if (d.getCommune().getDistrict().getRegion() != null) {
                    this.regionNom = d.getCommune().getDistrict().getRegion().getNom();
                }
            }
        }

        this.travailTermine = false;
        this.dureeTotaleTravail = "Travail pas encore terminé";

        List<DemandeStatut> listStatuts = d.getListDemandeStatut();
        if (listStatuts != null && !listStatuts.isEmpty()) {

            boolean aUnStatutTermine = false;
            for (DemandeStatut ds : listStatuts) {
                if (ds.getStatut() != null && ds.getStatut().getId() == 7) {
                    aUnStatutTermine = true;
                    break;
                }
            }

            if (aUnStatutTermine) {
                this.travailTermine = true;

                float sommeMinutes = 0;
                for (DemandeStatut ds : listStatuts) {
                    sommeMinutes += ds.getDureeTravail();
                }

                int heures = (int) (sommeMinutes / 60);
                int minutes = (int) (sommeMinutes % 60);
                this.dureeTotaleTravail = heures + "h " + minutes + "m";
            }
        }
    }

    public int getId() {
        return id;
    }

    public String getLieu() {
        return lieu;
    }

    public String getReference() {
        return reference;
    }

    public String getCommuneNom() {
        return communeNom;
    }

    public String getDistrictNom() {
        return districtNom;
    }

    public String getRegionNom() {
        return regionNom;
    }

    public String getClientNom() {
        return clientNom;
    }

    public String getDureeTotaleTravail() {
        return dureeTotaleTravail;
    }

    public boolean isTravailTermine() {
        return travailTermine;
    }
}