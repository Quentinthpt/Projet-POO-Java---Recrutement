package Modele;
import java.util.*;

public class DemandeurEmploi extends Utilisateur{
    private String lienCv;
    private ArrayList<String> competences;
    private ArrayList<String> experiences;
    private String formation;
    private Date date;
    private String typeUtilisateur;

    public String getLienCv() {
            return lienCv;
    }
    public ArrayList<String> getCompetences() {
        return competences;
    }
    public ArrayList<String> getExperiences() {
        return experiences;
    }
    public String getFormation() {
        return formation;
    }
    public Date getDate() {
        return date;
    }
    public String getTypeUtilisateur() {
        return typeUtilisateur;
    }
    public void setLienCv(String lienCv) {
        this.lienCv = lienCv;
    }
    public void setCompetences(ArrayList<String> competences) {
        this.competences = competences;
    }
    public void setExperiences(ArrayList<String> experiences) {
        this.experiences = experiences;
    }
    public void setFormation(String formation) {
        this.formation = formation;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public void setTypeUtilisateur(String typeUtilisateur) {
        this.typeUtilisateur = typeUtilisateur;
    }
}
