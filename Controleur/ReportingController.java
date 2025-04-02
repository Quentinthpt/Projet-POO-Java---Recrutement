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

    public Map<String, Integer> générerRapportEmploisPopulaires() {
        Map<String, Integer> rapport = new HashMap<>();
        List<Map<String, Object>> stats = offreEmploiDAO.findTopOffres();
        for (Map<String, Object> entry : stats) {
            String titreOffre = (String) entry.get("titre");
            int nombreCandidats = (int) entry.get("nombre_candidats");
            rapport.put(titreOffre, nombreCandidats);
        }
        return rapport;
    }

    public Map<String, Integer> générerRapportCandidatures() {
        Map<String, Integer> rapport = new HashMap<>();
        int enAttente = candidatureDAO.countByStatut("en attente");
        int acceptees = candidatureDAO.countByStatut("acceptée");
        int refusees = candidatureDAO.countByStatut("refusée");

        rapport.put("En attente", enAttente);
        rapport.put("Acceptées", acceptees);
        rapport.put("Refusées", refusees);

        return rapport;
    }

    public Map<String, Integer> générerRapportActivitéAgence(int idAgence) {
        Map<String, Integer> rapport = new HashMap<>();
        int offresPubliées = offreEmploiDAO.countByAgence(idAgence);
        int candidaturesReçues = candidatureDAO.countByAgence(idAgence);

        rapport.put("Offres publiées", offresPubliées);
        rapport.put("Candidatures reçues", candidaturesReçues);

        return rapport;
    }
}

