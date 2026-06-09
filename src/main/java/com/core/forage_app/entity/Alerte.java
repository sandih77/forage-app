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

    float dureeTravail1;

    float dureeTravail2;

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

    public float getDureeTravail1() {
        return dureeTravail1;
    }

    public void setDureeTravail1(float dureeTravail1) {
        this.dureeTravail1 = dureeTravail1;
    }

    public float getDureeTravail2() {
        return dureeTravail2;
    }

    public void setDureeTravail2(float dureeTravail2) {
        this.dureeTravail2 = dureeTravail2;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }
}
