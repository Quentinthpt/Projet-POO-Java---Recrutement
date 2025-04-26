package Controleur;
import DAO.OffreEmploiDAO;
import Modele.OffreEmploi;

public class OffreEmploiController {

        private OffreEmploiDAO offreEmploiDAO;

        public OffreEmploiController(OffreEmploiDAO offreDAO) {
            this.offreEmploiDAO = offreDAO;
        }

        public boolean creerOffre(OffreEmploi offre) {
            return false;
        }

        public boolean modifierOffre(OffreEmploi offre) {
            return false;
        }

        public boolean supprimerOffre(int id) {
            return false;
        }
    }


