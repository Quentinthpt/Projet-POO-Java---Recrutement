package Controleur;
import DAO.OffreEmploiDAO;
import DAO.CandidatureDAO;
import DAO.DemandeurEmploiDAO;
import Modele.Candidature;

import java.util.List;
import java.util.Map;

public class CandidatureController {
    private CandidatureDAO candidatureDAO;
    private DemandeurEmploiDAO demandeurDAO;
    private OffreEmploiDAO offreDAO;

    public CandidatureController(CandidatureDAO candidatureDAO, DemandeurEmploiDAO demandeurDAO, OffreEmploiDAO offreDAO) {
        this.candidatureDAO = candidatureDAO;
        this.demandeurDAO = demandeurDAO;
        this.offreDAO = offreDAO;
    }

    public boolean creerCandidature(Candidature candidature) {
        return false;
    }

    public boolean modifierStatut(int id, String statut) {
        return false;
    }

    public List<Candidature> consulterCandidatures(Map<String, String> filtres) {
        return null;
    }

}
