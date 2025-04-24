package Modele;

import java.util.Date;

public class Annonce {
    private int id;
    private String titre;
    private String description;
    private String experienceRequise;
    private int salaire;
    private Date dateDebut;
    private String statut;
    private String lieuTravail;
    private String typeContrat;
    private int idAdmin;
    private int idSociete;
    private int idCategorie;

    // Constructeur
    public Annonce(int id, String titre, String description, String experienceRequise,
                   int salaire, Date dateDebut, String statut, String lieuTravail,
                   String typeContrat, int idAdmin, int idSociete, int idCategorie) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.experienceRequise = experienceRequise;
        this.salaire = salaire;
        this.dateDebut = dateDebut;
        this.statut = statut;
        this.lieuTravail = lieuTravail;
        this.typeContrat = typeContrat;
        this.idAdmin = idAdmin;
        this.idSociete = idSociete;
        this.idCategorie = idCategorie;
    }

    public Annonce() {
        this.id = 0;
        this.titre = "";
        this.description = "";
        this.experienceRequise = "";
        this.salaire = 0;
        this.dateDebut = null;
        this.statut = "";
        this.lieuTravail = "";
        this.typeContrat = "";
        this.idAdmin = 0;
        this.idSociete = 0;
        this.idCategorie = 0;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getExperienceRequise() { return experienceRequise; }
    public void setExperienceRequise(String experienceRequise) { this.experienceRequise = experienceRequise; }
    public int getSalaire() { return salaire; }
    public void setSalaire(int salaire) { this.salaire = salaire; }
    public Date getDateDebut() { return dateDebut; }
    public void setDateDebut(Date dateDebut) { this.dateDebut = dateDebut; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    public String getLieuTravail() { return lieuTravail; }
    public void setLieuTravail(String lieuTravail) { this.lieuTravail = lieuTravail; }
    public String getTypeContrat() { return typeContrat; }
    public void setTypeContrat(String typeContrat) { this.typeContrat = typeContrat; }
    public int getIdAdmin() { return idAdmin; }
    public void setIdAdmin(int idAdmin) { this.idAdmin = idAdmin; }
    public int getIdSociete() { return idSociete; }
    public void setIdSociete(int idSociete) { this.idSociete = idSociete; }
    public int getIdCategorie() { return idCategorie; }
    public void setIdCategorie(int idCategorie) { this.idCategorie = idCategorie; }

    public void setIdAnnonce(int idAnnonce) {
        this.id = idAnnonce;
    }
}