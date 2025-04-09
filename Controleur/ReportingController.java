package Controleur;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DAO.OffreEmploiDAO;
import DAO.CandidatureDAO;
import DAO.AgenceRecrutementDAO;

public class ReportingController {
    private OffreEmploiDAO offreEmploiDAO;
    private CandidatureDAO candidatureDAO;
    private AgenceRecrutementDAO agenceRecrutementDAO;

    public ReportingController(OffreEmploiDAO offreEmploiDAO, CandidatureDAO candidatureDAO, AgenceRecrutementDAO agenceRecrutementDAO) {
        this.offreEmploiDAO = offreEmploiDAO;
        this.candidatureDAO = candidatureDAO;
        this.agenceRecrutementDAO = agenceRecrutementDAO;
    }

    public Map<String, Integer> genererRapportEmploisPopulaires() {
        Map<String, Integer> rapport = new HashMap<>();
        List<Map<String, Object>> stats = offreEmploiDAO.findTopOffres();
        for (Map<String, Object> entry : stats) {
            String titreOffre = (String) entry.get("titre");
            int nombreCandidats = (int) entry.get("nombre_candidats");
            rapport.put(titreOffre, nombreCandidats);
        }
        return rapport;
    }

    public Map<String, Integer> genererRapportCandidatures() {
        Map<String, Integer> rapport = new HashMap<>();
        int enAttente = candidatureDAO.countByStatut("en attente");
        int acceptees = candidatureDAO.countByStatut("accept�e");
        int refusees = candidatureDAO.countByStatut("refus�e");

        rapport.put("En attente", enAttente);
        rapport.put("Accept�es", acceptees);
        rapport.put("Refus�es", refusees);

        return rapport;
    }

    public Map<String, Integer> genererRapportActiviteAgence(int idAgence) {
        Map<String, Integer> rapport = new HashMap<>();
        int offresPubliees = offreEmploiDAO.countByAgence(idAgence);
        int candidaturesRecues = candidatureDAO.countByAgence(idAgence);

        rapport.put("Offres publi�es", offresPubliees);
        rapport.put("Candidatures re�ues", candidaturesRecues);

        return rapport;
    }
}

