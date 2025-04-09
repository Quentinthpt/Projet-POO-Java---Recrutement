package Controleur;

import DAO.UtilisateurDAO;
import Modele.Utilisateur;

public class AuthentificationController {
    private UtilisateurDAO utilisateurDAO;

    public AuthentificationController(UtilisateurDAO utilisateurDAO) {
        this.utilisateurDAO = utilisateurDAO;
    }

    public UtilisateurDAO getUtilisateurDAO() {
        return utilisateurDAO;
    }
    public Utilisateur connecter(String login, String password) {
        // Impl�mentation � faire
        return null;
    }
    public boolean inscrire(Utilisateur utilisateur){
        return false;
    }
    public boolean deconnecter() {
        // Impl�mentation � faire
        return false;
    }

    public boolean recupererMotDePasse(String email) {
        // Impl�mentation � faire
        return false;
    }
}
