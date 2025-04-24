package Modele;

public class AgenceRecrutement extends Utilisateur{
    private String nomAgence;
    private String adresse;
    private String telephone;
    private String secteurActivite;
    private String description;

    public AgenceRecrutement(String nom, String prenom, String email, String motDePasse, String type) {
        super(nom, prenom, email, motDePasse, type);
    }

    public String getNomAgence() {
        return nomAgence;
    }
    public String getAdresse() {
        return adresse;
    }
    public String getTelephone() {
        return telephone;
    }
    public String getSecteurActivite() {
        return secteurActivite;
    }
    public String getDescription() {
        return description;
    }
    public void setNomAgence(String nomAgence) {
        this.nomAgence = nomAgence;
    }
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public void setSecteurActivite(String secteurActivite) {
        this.secteurActivite = secteurActivite;
    }
    public void setDescription(String description) {
        this.description = description;
    }

}
