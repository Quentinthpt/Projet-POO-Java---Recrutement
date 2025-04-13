package Modele;
import java.util.*;


public class Utilisateur {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private int age;
    private String adresse;
    private String experience;
    private String cv;
    private String type;
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
    public int getAge() {return age;}
    public String getAdresse() {return adresse;}
    public String getExperience() {return experience;}
    public String getCv() {return cv;}
    public String getType() {return type;}
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

    public Utilisateur(String nom, String prenom, String email, String motDePasse, String type) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.type = type;
    }

    public Utilisateur(String nom, String prenom, int age, String email, String adresse, String experience, String cv, String motDePasse, String type) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.age = age;
        this.adresse = adresse;
        this.experience = experience;
        this.cv = cv;
        this.type = "Demandeur";
    }


}
