package DAO;

import Modele.Candidature;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CandidatureDAOImpl {

    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/recrutement";
        String user = "root";
        String password = "";
        return DriverManager.getConnection(url, user, password);
    }



    public void ajouterCandidature(Candidature candidature) {
        // Affichage des informations de la candidature à ajouter
        System.out.println(">>> Début de la méthode ajouterCandidature()");
        System.out.println("ID Annonce      : " + candidature.getIdAnnonce());
        System.out.println("ID Demandeur    : " + candidature.getIdDemandeur());
        System.out.println("Date Candidature : " + candidature.getDateCandidature());
        System.out.println("Statut Candidature: " + candidature.getStatut());
        System.out.println("Note Candidature : " + candidature.getNote());
        System.out.println("Documents Candidature : " + candidature.getDocuments());

        String sql = "INSERT INTO candidature (id_annonce, id_demandeurs, date_candidature, statut_candidature, note_candidature, documents_candidature) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Log de l'ouverture de la connexion à la base de données
            System.out.println("Connexion à la base de données établie.");

            stmt.setInt(1, candidature.getIdAnnonce());
            stmt.setInt(2, candidature.getIdDemandeur());
            stmt.setDate(3, new java.sql.Date(candidature.getDateCandidature().getTime()));
            stmt.setString(4, candidature.getStatut());
            stmt.setInt(5, candidature.getNote());
            stmt.setString(6, candidature.getDocuments());

            // Log avant l'exécution de la requête
            System.out.println("Exécution de la requête SQL : " + sql);

            stmt.executeUpdate();

            // Log après l'exécution de la requête
            System.out.println("Candidature ajoutée avec succès dans la base de données.");
        } catch (SQLException e) {
            // En cas d'erreur SQL, on affiche le message d'erreur et la pile d'appel
            System.out.println("Erreur SQL lors de l'ajout de la candidature.");
            e.printStackTrace();
        }

        System.out.println(">>> Fin de la méthode ajouterCandidature()");
    }


    public boolean updateCandidature(Candidature candidature) {
        String sql = "UPDATE candidature SET date_candidature = ?, statut_candidature = ?, note_candidature = ?, documents_candidature = ? " +
                "WHERE id_annonce = ? AND id_demandeurs = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(candidature.getDateCandidature().getTime()));
            stmt.setString(2, candidature.getStatut());
            stmt.setInt(3, candidature.getNote());
            stmt.setString(4, candidature.getDocuments());
            stmt.setInt(5, candidature.getIdAnnonce());
            stmt.setInt(6, candidature.getIdDemandeur());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCandidature(int idAnnonce, int idDemandeurs) {
        String sql = "DELETE FROM candidature WHERE id_annonce = ? AND id_demandeurs = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAnnonce);
            stmt.setInt(2, idDemandeurs);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Candidature getCandidature(int idAnnonce, int idDemandeurs) {
        String sql = "SELECT * FROM candidature WHERE id_annonce = ? AND id_demandeurs = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAnnonce);
            stmt.setInt(2, idDemandeurs);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Candidature candidature = new Candidature();
                candidature.setIdAnnonce(rs.getInt("id_annonce"));
                candidature.setIdDemandeur(rs.getInt("id_demandeurs"));
                candidature.setDateCandidature(rs.getDate("date_candidature"));
                candidature.setStatut(rs.getString("statut_candidature"));
                candidature.setNote(rs.getInt("note_candidature"));
                candidature.setDocuments(rs.getString("documents_candidature"));
                return candidature;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Candidature> getCandidaturesByIdDemandeur(int idDemandeurs) {
        List<Candidature> candidatures = new ArrayList<>();
        String sql = "SELECT * FROM candidature WHERE id_demandeurs = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idDemandeurs);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Candidature candidature = new Candidature();
                candidature.setIdAnnonce(rs.getInt("id_annonce"));
                candidature.setIdDemandeur(rs.getInt("id_demandeurs"));
                candidature.setDateCandidature(rs.getDate("date_candidature"));
                candidature.setStatut(rs.getString("statut_candidature"));
                candidature.setNote(rs.getInt("note_candidature"));
                candidature.setDocuments(rs.getString("documents_candidature"));
                candidatures.add(candidature);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return candidatures;
    }

    public List<String[]> getInfosAnnoncesCandidature(int idDemandeur){
        List<String[]> data = new ArrayList<>();
        String sql = """
        SELECT a.titre_annonce, a.description_annonce, a.salaire_annonce, a.lieu_travail_annonce,
               a.type_contrat_annonce, a.experience_requise_annonce, a.date_debut_annonce
        FROM candidature c
        JOIN annonce a ON c.id_annonce = a.id_annonce
        WHERE c.id_demandeurs = ?
    """;
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idDemandeur);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String[] row = new String[7];
                row[0] = rs.getString("titre_annonce");
                row[1] = rs.getString("description_annonce");
                row[2] = rs.getString("salaire_annonce");
                row[3] = rs.getString("lieu_travail_annonce");
                row[4] = rs.getString("type_contrat_annonce");
                row[5] = rs.getString("experience_requise_annonce");
                row[6] = rs.getString("date_debut_annonce");
                data.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }
}
