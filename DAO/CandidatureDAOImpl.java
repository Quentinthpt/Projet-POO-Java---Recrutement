package DAO;

import Modele.Candidature;
import javax.swing.JOptionPane; // Ajout de cet import pour JOptionPane

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
        System.out.println(">>> Début de la méthode ajouterCandidature()");
        System.out.println("ID Annonce: " + candidature.getIdAnnonce());
        System.out.println("ID Demandeur: " + candidature.getIdDemandeur());

        // Vérification plus robuste de l'existence de la candidature
        boolean candidatureExiste = verifierCandidatureExistante(candidature.getIdAnnonce(), candidature.getIdDemandeur());

        if (candidatureExiste) {
            String nomDemandeur = getNomDemandeur(candidature.getIdDemandeur());
            System.out.println("Candidature existe déjà pour " + nomDemandeur);

            JOptionPane.showMessageDialog(
                    null,
                    nomDemandeur + ", vous avez déjà postulé à cette offre.",
                    "Candidature existante",
                    JOptionPane.ERROR_MESSAGE
            );
        }else {

            // Ajout de la candidature
            String sql = "INSERT INTO candidature (id_annonce, id_demandeurs, date_candidature, "
                    + "statut_candidature, note_candidature, documents_candidature) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";

            try (Connection conn = getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, candidature.getIdAnnonce());
                stmt.setInt(2, candidature.getIdDemandeur());
                stmt.setDate(3, new java.sql.Date(candidature.getDateCandidature().getTime()));
                stmt.setString(4, candidature.getStatut());
                stmt.setInt(5, candidature.getNote());
                stmt.setString(6, candidature.getDocuments());

                int rowsAffected = stmt.executeUpdate();
                System.out.println(rowsAffected + " ligne(s) affectée(s)");

            } catch (SQLException e) {
                System.out.println("Erreur SQL lors de l'ajout: " + e.getMessage());
                JOptionPane.showMessageDialog(
                        null,
                        "Erreur technique lors de la candidature",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE
                );
            }

            System.out.println(">>> Fin de la méthode ajouterCandidature()");
        }

    }
    // Méthode pour vérifier si une candidature existe déjà pour ce demandeur et cette annonce
    private boolean verifierCandidatureExistante(int idAnnonce, int idDemandeur) {
        String sql = "SELECT COUNT(*) FROM candidature WHERE id_annonce = ? AND id_demandeurs = ?";
        boolean existe = false;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAnnonce);
            stmt.setInt(2, idDemandeur);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Si le compte est supérieur à 0, la candidature existe déjà
                    existe = rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la vérification de candidature existante.");
            JOptionPane.showMessageDialog(
                    null,
                    "Ne postulez pas en double...",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }

        return existe;
    }

    // Méthode pour obtenir le nom du demandeur à partir de son ID
    private String getNomDemandeur(int idDemandeur) {
        String sql = "SELECT nom FROM demandeurs WHERE id_demandeurs = ?";
        String nom = "Utilisateur"; // Valeur par défaut si le nom n'est pas trouvé

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idDemandeur);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    nom = rs.getString("nom");
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du nom du demandeur.");
            e.printStackTrace();
        }

        return nom;
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

    public List<Candidature> getAllCandidatures() throws SQLException {
        List<Candidature> candidatures = new ArrayList<>();
        String sql = "SELECT * FROM candidature";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

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
        }
        return candidatures;
    }

    public Candidature getCandidatureById(int idCandidature) throws SQLException {
        String sql = "SELECT * FROM candidature WHERE id_candidature = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idCandidature);
            
            try (ResultSet rs = stmt.executeQuery()) {
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
            }
        }
        return null;
    }

    public boolean updateCandidature(Candidature candidature) throws SQLException {
        String sql = "UPDATE candidature SET statut_candidature = ?, note_candidature = ? WHERE id_annonce = ? AND id_demandeurs = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, candidature.getStatut());
            stmt.setInt(2, candidature.getNote());
            stmt.setInt(3, candidature.getIdAnnonce());
            stmt.setInt(4, candidature.getIdDemandeur());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public boolean deleteCandidature(int idCandidature) throws SQLException {
        String sql = "DELETE FROM candidature WHERE id_candidature = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idCandidature);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
}
