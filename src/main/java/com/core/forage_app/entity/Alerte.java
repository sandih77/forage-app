package com.core.forage_app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "alerte")
public class Alerte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "id_statut1")
    Statut statut1;

    @ManyToOne
    @JoinColumn(name = "id_statut2")
    Statut statut2;

    float dureeTravail;

    String couleur;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Statut getStatut1() {
        return statut1;
    }

    public void setStatut1(Statut statut1) {
        this.statut1 = statut1;
    }

    public Statut getStatut2() {
        return statut2;
    }

    public void setStatut2(Statut statut2) {
        this.statut2 = statut2;
    }

    public float getDureeTravail() {
        return dureeTravail;
    }

    public void setDureeTravail(float dureeTravail) {
        this.dureeTravail = dureeTravail;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }
}
