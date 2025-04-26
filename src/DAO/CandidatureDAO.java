package DAO;

import Modele.Candidature;

import java.sql.SQLException;
import java.util.List;

public interface CandidatureDAO {
    int countByStatut(String enAttente);

    int countByAgence(int idAgence);

    public Candidature getCandidateByEmail(String email) ;
    public void ajouterCandidature(int idAnnonce, int idDemandeur, String documents);
    public Candidature getInfosAnnoncesCandidature(int idDemandeur);
    public boolean updateCandidature(Candidature candidature) throws SQLException;
    public List<Candidature> getAllCandidatures() throws SQLException;
    public boolean deleteCandidature(int idAnnonce, int idDemandeurs) throws SQLException;
    public boolean existeCandidature(int idDemandeur, int idAnnonce) throws SQLException;
    public int getIdAnnonceFromTitre(String titre) throws SQLException;
}