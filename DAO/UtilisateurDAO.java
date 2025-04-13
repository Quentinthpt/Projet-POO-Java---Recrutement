package DAO;

import Modele.Utilisateur;

import java.sql.SQLException;

public interface UtilisateurDAO {
    Utilisateur connecter(String email, String password) throws SQLException;
    boolean inscrireDemandeur(Utilisateur utilisateur);
}
