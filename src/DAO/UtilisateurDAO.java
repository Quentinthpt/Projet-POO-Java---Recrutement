package DAO;

import Modele.SessionUtilisateur;
import Modele.Utilisateur;

import java.sql.SQLException;

public interface UtilisateurDAO {
    Utilisateur connecter(String email, String password) throws SQLException;
    boolean inscrireDemandeur(Utilisateur utilisateur);
    boolean mettreAJourProfil(SessionUtilisateur utilisateur);
}
