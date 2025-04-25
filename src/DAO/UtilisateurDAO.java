package DAO;

import Modele.SessionUtilisateur;
import Modele.Utilisateur;

import java.sql.SQLException;

public interface UtilisateurDAO {
    Utilisateur connecter(String email, String password) throws SQLException;
    boolean inscrireDemandeur(Utilisateur utilisateur);
    public boolean utilisateurExiste(String email) throws SQLException;
    boolean mettreAJourProfil(SessionUtilisateur utilisateur);
}
