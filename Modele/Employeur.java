package Modele;

public class Employeur {
    private String nomEntreprise;
    private String secteurActivite;
    private String adresse;
    private String telephone;
    private String email;
    private String description;

    // tous les getter

    public String getNomEntreprise() {
        return nomEntreprise;
    }
    public String getSecteurActivite() {
        return secteurActivite;
    }
    public String getAdresse() {
        return adresse;
    }
    public String getTelephone() {
        return telephone;
    }
    public String getEmail() {
        return email;
    }
    public String getDescription() {
        return description;
    }

    // tous les setter

    public void setNomEntreprise(String nomEntreprise) {
        this.nomEntreprise = nomEntreprise;
    }
    public void setSecteurActivite(String secteurActivite) {
        this.secteurActivite = secteurActivite;
    }
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setDescription(String description) {
        this.description = description;
    }


}
