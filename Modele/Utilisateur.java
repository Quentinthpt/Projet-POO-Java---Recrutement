package Modele;
import java.util.*;


public class Utilisateur {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    public Date dateInscription;

    public int getId() {
        return id;
    }
    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public String getEmail() {
        return email;
    }
    public String getMotDePasse() {
        return motDePasse;
    }
    public Date getDateInscription() {
        return dateInscription;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
    }

}
