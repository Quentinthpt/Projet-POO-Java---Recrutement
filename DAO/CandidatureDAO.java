package DAO;

public interface CandidatureDAO {
    int countByStatut(String enAttente);

    int countByAgence(int idAgence);
}
