package DAO;

import Modele.Annonce;
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

    // Ajouter une nouvelle annonce
    public boolean addAnnonce(Annonce annonce) throws SQLException {
        try (Connection con = getConnection()) {
            String sql = "INSERT INTO annonce (titre_annonce, description_annonce, experience_requise_annonce, "
                    + "salaire_annonce, date_debut_annonce, statut_annonce, lieu_travail_annonce, "
                    + "type_contrat_annonce, id_admin, id_societe, id_categorie) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, annonce.getTitre());
            ps.setString(2, annonce.getDescription());
            ps.setString(3, annonce.getExperienceRequise());
            ps.setInt(4, annonce.getSalaire());
            ps.setDate(5, new java.sql.Date(annonce.getDateDebut().getTime()));
            ps.setString(6, annonce.getStatut());
            ps.setString(7, annonce.getLieuTravail());
            ps.setString(8, annonce.getTypeContrat());
            ps.setInt(9, annonce.getIdAdmin());
            ps.setInt(10, annonce.getIdSociete());
            ps.setInt(11, annonce.getIdCategorie());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Mettre à jour une annonce existante
    public boolean updateAnnonce(Annonce annonce) throws SQLException {
        try (Connection con = getConnection()) {
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
            ps.setInt(9, annonce.getIdAdmin());
            ps.setInt(10, annonce.getIdSociete());
            ps.setInt(11, annonce.getIdCategorie());
            ps.setInt(12, annonce.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Supprimer une annonce
    public boolean deleteAnnonce(int id) throws SQLException {
        try (Connection con = getConnection()) {
            String sql = "DELETE FROM annonce WHERE id_annonce = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
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
}