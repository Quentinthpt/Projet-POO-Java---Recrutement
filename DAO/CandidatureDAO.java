package DAO;

import Modele.Candidature;

public interface CandidatureDAO {
    int countByStatut(String enAttente);

    int countByAgence(int idAgence);

    public Candidature getCandidateByEmail(String email) ;
    public void ajouterCandidature(int idAnnonce, int idDemandeur, String documents);
}
