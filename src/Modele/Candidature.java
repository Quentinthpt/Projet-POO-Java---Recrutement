package Modele;

import java.util.Date;

public class Candidature {
    private int idAnnonce;
    private int idDemandeur;
    private Date dateCandidature;
    private String statut;
    private int note;
    private String documents;

    // Constructeurs
    public Candidature() {
        this.idAnnonce = 0;
        this.idDemandeur = 0;
        this.dateCandidature = null;
        this.statut = "";
        this.note = 0;
        this.documents = "";
    }

    public Candidature(int idAnnonce, int idDemandeur, Date dateCandidature, String statut, int note, String documents) {
        this.idAnnonce = idAnnonce;
        this.idDemandeur = idDemandeur;
        this.dateCandidature = dateCandidature;
        this.statut = statut;
        this.note = note;
        this.documents = documents;
    }

    // Getters
    public int getIdAnnonce() {
        return idAnnonce;
    }

    public int getIdDemandeur() {
        return idDemandeur;
    }

    public Date getDateCandidature() {
        return dateCandidature;
    }

    public String getStatut() {
        return statut;
    }

    public int getNote() {
        return note;
    }

    public String getDocuments() {
        return documents;
    }

    // Setters
    public void setIdAnnonce(int idAnnonce) {
        this.idAnnonce = idAnnonce;
    }

    public void setIdDemandeur(int idDemandeur) {
        this.idDemandeur = idDemandeur;
    }

    public void setDateCandidature(Date dateCandidature) {
        this.dateCandidature = dateCandidature;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
    }
}
