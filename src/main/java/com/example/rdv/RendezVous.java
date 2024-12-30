package com.example.rdv;

import java.io.Serializable;
import java.util.Date;

public class RendezVous implements Serializable {
    private int id;
    private Date date;  // Date du rendez-vous (sans l'heure)
    private String motif;    // Motif du rendez-vous
    private String statut;   // Statut du rendez-vous (en attente, confirmé, annulé)
    private int patientId;   // ID du patient
    private int medecinId;   // ID du médecin

    // Constructeur par défaut
    public RendezVous() {
    }

    // Constructeur avec tous les paramètres
    public RendezVous(int id, Date date, String motif, String statut, int patientId, int medecinId) {
        this.id = id;
        this.date = date;
        this.motif = motif;
        this.statut = statut;
        this.patientId = patientId;
        this.medecinId = medecinId;
    }

    // Getters et setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getMedecinId() {
        return medecinId;
    }

    public void setMedecinId(int medecinId) {
        this.medecinId = medecinId;
    }

    @Override
    public String toString() {
        return "RendezVous{" +
                "id=" + id +
                ", date=" + date +
                ", motif='" + motif + '\'' +
                ", statut='" + statut + '\'' +
                ", patientId=" + patientId +
                ", medecinId=" + medecinId +
                '}';
    }
}
