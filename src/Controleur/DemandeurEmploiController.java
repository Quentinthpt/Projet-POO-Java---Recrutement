package Controleur;
import DAO.*;
import Modele.DemandeurEmploi;
import Modele.OffreEmploi;

import java.util.*;

public class DemandeurEmploiController {
    private DemandeurEmploiDAO demandeurDAO;
    private OffreEmploiDAO offreDAO;
    private CandidatureDAO candidatureDAO;

    public DemandeurEmploiController(DemandeurEmploiDAO demandeurDAO, OffreEmploiDAO offreDAO, CandidatureDAO candidatureDAO) {
        this.demandeurDAO = demandeurDAO;
        this.offreDAO = offreDAO;
        this.candidatureDAO = candidatureDAO;
    }

    public boolean modifierProfil(DemandeurEmploi demandeur) {
        return false;
    }

    public List<OffreEmploi> rechercherEmplois(Map<String, String> filtres) {
        return null;
    }
}