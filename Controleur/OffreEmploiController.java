package Controleur;
import DAO.CategorieDAO;
import DAO.OffreEmploiDAO;
import Modele.OffreEmploi;

public class OffreEmploiController {

        private OffreEmploiDAO offreEmploiDAO;
        private CategorieDAO categorieDAO;

        public OffreEmploiController(OffreEmploiDAO offreDAO, CategorieDAO categorieDAO) {
            this.offreEmploiDAO = offreDAO;
            this.categorieDAO = categorieDAO;
        }

        public boolean cr�erOffre(OffreEmploi offre) {
            return false;
        }

        public boolean modifierOffre(OffreEmploi offre) {
            return false;
        }

        public boolean supprimerOffre(int id) {
            return false;
        }
    }


