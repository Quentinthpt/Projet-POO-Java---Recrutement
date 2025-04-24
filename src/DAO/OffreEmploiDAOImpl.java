package DAO;

import Modele.Annonce;
import Modele.SessionUtilisateur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OffreEmploiDAOImpl {
    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/recrutement";
        String user = "root";
        String password = "";
        return DriverManager.getConnection(url, user, password);
    }

    // Récupérer toutes les annonces
    public List<Annonce> getAllAnnonces() throws SQLException {
        List<Annonce> annonces = new ArrayList<>();
        try (Connection con = getConnection()) {
            String sql = "SELECT * FROM annonce";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Annonce annonce = new Annonce(
                        rs.getInt("id_annonce"),
                        rs.getString("titre_annonce"),
                        rs.getString("description_annonce"),
                        rs.getString("experience_requise_annonce"),
                        rs.getInt("salaire_annonce"),
                        rs.getDate("date_debut_annonce"),
                        rs.getString("statut_annonce"),
                        rs.getString("lieu_travail_annonce"),
                        rs.getString("type_contrat_annonce"),
                        rs.getInt("id_admin"),
                        rs.getInt("id_societe"),
                        rs.getInt("id_categorie")
                );
                annonces.add(annonce);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return annonces;
    }

    // Récupérer une annonce par son ID
    public Annonce getAnnonceById(int id) throws SQLException {
        try (Connection con = getConnection()) {
            String sql = "SELECT * FROM annonce WHERE id_annonce = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Annonce(
                        rs.getInt("id_annonce"),
                        rs.getString("titre_annonce"),
                        rs.getString("description_annonce"),
                        rs.getString("experience_requise_annonce"),
                        rs.getInt("salaire_annonce"),
                        rs.getDate("date_debut_annonce"),
                        rs.getString("statut_annonce"),
                        rs.getString("lieu_travail_annonce"),
                        rs.getString("type_contrat_annonce"),
                        rs.getInt("id_admin"),
                        rs.getInt("id_societe"),
                        rs.getInt("id_categorie")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return null;
    }

    // Vérifier si un administrateur existe
    private boolean adminExists(int idAdmin, Connection con) throws SQLException {
        String sql = "SELECT COUNT(*) FROM administrateurs WHERE id_admin = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, idAdmin);
        ResultSet rs = ps.executeQuery();
        return rs.next() && rs.getInt(1) > 0;
    }

    // Ajouter une nouvelle annonce
    public boolean addAnnonce(Annonce annonce) throws SQLException {
        Connection con = null;
        try {
            con = getConnection();
            con.setAutoCommit(false);

            // Debug: Afficher l'ID de l'administrateur
            System.out.println("Debug - ID Administrateur reçu : " + SessionUtilisateur.getInstance().getId());

            // Vérifier si l'administrateur existe
            if (!adminExists(SessionUtilisateur.getInstance().getId(), con)) {
                System.out.println("Debug - L'administrateur n'existe pas dans la base de données");
                // Afficher tous les administrateurs disponibles pour le débogage
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT id_admin FROM administrateurs");
                System.out.println("Debug - Liste des IDs d'administrateurs disponibles :");
                while (rs.next()) {
                    System.out.println("ID Admin disponible : " + rs.getInt("id_admin"));
                }
                throw new SQLException("L'administrateur avec l'ID " + annonce.getIdAdmin() + " n'existe pas.");
            }

            System.out.println("Debug - L'administrateur existe, continuation de l'ajout de l'annonce");

            String sql = "INSERT INTO annonce (titre_annonce, description_annonce, experience_requise_annonce, " +
                    "salaire_annonce, date_debut_annonce, statut_annonce, lieu_travail_annonce, " +
                    "type_contrat_annonce, id_admin, id_societe, id_categorie) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, annonce.getTitre());
            ps.setString(2, annonce.getDescription());
            ps.setString(3, annonce.getExperienceRequise());
            ps.setInt(4, annonce.getSalaire());
            ps.setDate(5, new java.sql.Date(annonce.getDateDebut().getTime()));
            ps.setString(6, annonce.getStatut());
            ps.setString(7, annonce.getLieuTravail());
            ps.setString(8, annonce.getTypeContrat());
            ps.setInt(9, SessionUtilisateur.getInstance().getId());
            ps.setInt(10, 1);
            ps.setInt(11, 1);
            
            int result = ps.executeUpdate();
            con.commit();
            return result > 0;
            
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            throw e;
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Mettre à jour une annonce existante
    public boolean updateAnnonce(Annonce annonce) throws SQLException {
        Connection con = null;
        try {
            con = getConnection();
            con.setAutoCommit(false);

            // Vérifier si l'administrateur existe
            if (!adminExists(SessionUtilisateur.getInstance().getId(), con)) {
                throw new SQLException("L'administrateur avec l'ID " + annonce.getIdAdmin() + " n'existe pas.");
            }
            int idAdmin = SessionUtilisateur.getInstance().getId();
            
            String sql = "UPDATE annonce SET titre_annonce = ?, description_annonce = ?, "
                    + "experience_requise_annonce = ?, salaire_annonce = ?, date_debut_annonce = ?, "
                    + "statut_annonce = ?, lieu_travail_annonce = ?, type_contrat_annonce = ?, "
                    + "id_admin = ?, id_societe = ?, id_categorie = ? WHERE id_annonce = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, annonce.getTitre());
            ps.setString(2, annonce.getDescription());
            ps.setString(3, annonce.getExperienceRequise());
            ps.setInt(4, annonce.getSalaire());
            ps.setDate(5, new java.sql.Date(annonce.getDateDebut().getTime()));
            ps.setString(6, annonce.getStatut());
            ps.setString(7, annonce.getLieuTravail());
            ps.setString(8, annonce.getTypeContrat());
            ps.setInt(9, idAdmin);
            ps.setInt(10, annonce.getIdSociete());
            ps.setInt(11, annonce.getIdCategorie());
            ps.setInt(12, annonce.getId());

            int result = ps.executeUpdate();
            con.commit();
            return result > 0;
            
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            throw e;
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Supprimer une annonce et ses candidatures associées
    public boolean deleteAnnonce(int id) throws SQLException {
        Connection con = null;
        try {
            con = getConnection();
            con.setAutoCommit(false); // Début de la transaction
            
            // D'abord, supprimer les candidatures associées
            String deleteCandidatures = "DELETE FROM candidature WHERE id_annonce = ?";
            PreparedStatement psCandidatures = con.prepareStatement(deleteCandidatures);
            psCandidatures.setInt(1, id);
            psCandidatures.executeUpdate();
            
            // Ensuite, supprimer l'annonce
            String deleteAnnonce = "DELETE FROM annonce WHERE id_annonce = ?";
            PreparedStatement psAnnonce = con.prepareStatement(deleteAnnonce);
            psAnnonce.setInt(1, id);
            int result = psAnnonce.executeUpdate();
            
            con.commit(); // Valider la transaction
            return result > 0;
            
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback(); // Annuler la transaction en cas d'erreur
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            throw e;
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Recherche d'annonces par critères
    public List<Annonce> searchAnnonces(String keyword, String lieu, String typeContrat) throws SQLException {
        List<Annonce> annonces = new ArrayList<>();
        try (Connection con = getConnection()) {
            String sql = "SELECT * FROM annonce WHERE "
                    + "(titre_annonce LIKE ? OR description_annonce LIKE ?) "
                    + "AND lieu_travail_annonce LIKE ? "
                    + "AND type_contrat_annonce LIKE ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            ps.setString(3, "%" + lieu + "%");
            ps.setString(4, "%" + typeContrat + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Annonce annonce = new Annonce(
                        rs.getInt("id_annonce"),
                        rs.getString("titre_annonce"),
                        rs.getString("description_annonce"),
                        rs.getString("experience_requise_annonce"),
                        rs.getInt("salaire_annonce"),
                        rs.getDate("date_debut_annonce"),
                        rs.getString("statut_annonce"),
                        rs.getString("lieu_travail_annonce"),
                        rs.getString("type_contrat_annonce"),
                        rs.getInt("id_admin"),
                        rs.getInt("id_societe"),
                        rs.getInt("id_categorie")
                );
                annonces.add(annonce);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return annonces;
    }

    // Nouvelle méthode : récupérer une annonce par son titre
    public Annonce getAnnonceByTitre(String titre) throws SQLException {
        try (Connection con = getConnection()) {
            String sql = "SELECT * FROM annonce WHERE titre_annonce = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, titre);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Annonce(
                        rs.getInt("id_annonce"),
                        rs.getString("titre_annonce"),
                        rs.getString("description_annonce"),
                        rs.getString("experience_requise_annonce"),
                        rs.getInt("salaire_annonce"),
                        rs.getDate("date_debut_annonce"),
                        rs.getString("statut_annonce"),
                        rs.getString("lieu_travail_annonce"),
                        rs.getString("type_contrat_annonce"),
                        rs.getInt("id_admin"),
                        rs.getInt("id_societe"),
                        rs.getInt("id_categorie")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return null;
    }

    // Récupérer les annonces par statut
    public List<Annonce> getAnnoncesByStatut(String statut) throws SQLException {
        List<Annonce> annonces = new ArrayList<>();
        try (Connection con = getConnection()) {
            String sql = "SELECT * FROM annonce WHERE statut_annonce = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, statut);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Annonce annonce = new Annonce(
                        rs.getInt("id_annonce"),
                        rs.getString("titre_annonce"),
                        rs.getString("description_annonce"),
                        rs.getString("experience_requise_annonce"),
                        rs.getInt("salaire_annonce"),
                        rs.getDate("date_debut_annonce"),
                        rs.getString("statut_annonce"),
                        rs.getString("lieu_travail_annonce"),
                        rs.getString("type_contrat_annonce"),
                        rs.getInt("id_admin"),
                        rs.getInt("id_societe"),
                        rs.getInt("id_categorie")
                );
                annonces.add(annonce);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return annonces;
    }

    // Récupérer les annonces par catégorie
    public List<Annonce> getAnnoncesByCategorie(int idCategorie) throws SQLException {
        List<Annonce> annonces = new ArrayList<>();
        try (Connection con = getConnection()) {
            String sql = "SELECT * FROM annonce WHERE id_categorie = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idCategorie);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Annonce annonce = new Annonce(
                        rs.getInt("id_annonce"),
                        rs.getString("titre_annonce"),
                        rs.getString("description_annonce"),
                        rs.getString("experience_requise_annonce"),
                        rs.getInt("salaire_annonce"),
                        rs.getDate("date_debut_annonce"),
                        rs.getString("statut_annonce"),
                        rs.getString("lieu_travail_annonce"),
                        rs.getString("type_contrat_annonce"),
                        rs.getInt("id_admin"),
                        rs.getInt("id_societe"),
                        rs.getInt("id_categorie")
                );
                annonces.add(annonce);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return annonces;
    }

    // Récupérer les annonces par société
    public List<Annonce> getAnnoncesBySociete(int idSociete) throws SQLException {
        List<Annonce> annonces = new ArrayList<>();
        try (Connection con = getConnection()) {
            String sql = "SELECT * FROM annonce WHERE id_societe = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idSociete);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Annonce annonce = new Annonce(
                        rs.getInt("id_annonce"),
                        rs.getString("titre_annonce"),
                        rs.getString("description_annonce"),
                        rs.getString("experience_requise_annonce"),
                        rs.getInt("salaire_annonce"),
                        rs.getDate("date_debut_annonce"),
                        rs.getString("statut_annonce"),
                        rs.getString("lieu_travail_annonce"),
                        rs.getString("type_contrat_annonce"),
                        rs.getInt("id_admin"),
                        rs.getInt("id_societe"),
                        rs.getInt("id_categorie")
                );
                annonces.add(annonce);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return annonces;
    }

    // Mettre à jour le statut d'une annonce
    public boolean updateStatutAnnonce(int id, String nouveauStatut) throws SQLException {
        try (Connection con = getConnection()) {
            String sql = "UPDATE annonce SET statut_annonce = ? WHERE id_annonce = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nouveauStatut);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Vérifier si une annonce existe
    public boolean annonceExists(int id) throws SQLException {
        try (Connection con = getConnection()) {
            String sql = "SELECT COUNT(*) FROM annonce WHERE id_annonce = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Méthode pour compter les candidatures pour une annonce
    public int countCandidatures(int idAnnonce) throws SQLException {
        try (Connection con = getConnection()) {
            String sql = "SELECT COUNT(*) FROM candidature WHERE id_annonce = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idAnnonce);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }

    public void ajouterAnnonce(Annonce nouvelleOffre) {
    }
}
