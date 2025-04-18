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

    /**
     * Ajoute une candidature dans la base de données
     * @param candidature La candidature à ajouter
     * @return true si l'ajout a réussi, false sinon
     * @throws SQLException En cas d'erreur SQL
     */
    public boolean ajouterCandidature(Candidature candidature) throws SQLException {
        System.out.println(">>> Début de la méthode ajouterCandidature()");
        System.out.println("ID Annonce      : " + candidature.getIdAnnonce());
        System.out.println("ID Demandeur    : " + candidature.getIdDemandeur());
        System.out.println("Date Candidature : " + candidature.getDateCandidature());
        System.out.println("Statut Candidature: " + candidature.getStatut());
        System.out.println("Note Candidature : " + candidature.getNote());
        System.out.println("Documents Candidature : " + candidature.getDocuments());

        Connection conn = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            String sql = "INSERT INTO candidature (id_annonce, id_demandeurs, date_candidature, statut_candidature, note_candidature, documents_candidature) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, candidature.getIdAnnonce());
            stmt.setInt(2, candidature.getIdDemandeur());
            stmt.setDate(3, new java.sql.Date(candidature.getDateCandidature().getTime()));
            stmt.setString(4, candidature.getStatut());
            stmt.setInt(5, candidature.getNote());
            stmt.setString(6, candidature.getDocuments());

            System.out.println("Exécution de la requête SQL : " + sql);

            int result = stmt.executeUpdate();
            conn.commit();

            System.out.println("Candidature ajoutée avec succès dans la base de données.");
            System.out.println(">>> Fin de la méthode ajouterCandidature()");

            return result > 0;

        } catch (SQLException e) {
            System.out.println("Erreur SQL lors de l'ajout de la candidature.");
            e.printStackTrace();

            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Met à jour une candidature existante
     * @param candidature La candidature à mettre à jour
     * @return true si la mise à jour a réussi, false sinon
     * @throws SQLException En cas d'erreur SQL
     */
    public boolean updateCandidature(Candidature candidature) throws SQLException {
        Connection conn = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            // Récupérer la candidature existante pour conserver les champs non modifiés
            Candidature existingCandidature = getCandidature(candidature.getIdAnnonce(), candidature.getIdDemandeur());

            if (existingCandidature == null) {
                throw new SQLException("La candidature n'existe pas.");
            }

            // Si des champs ne sont pas définis dans la candidature à mettre à jour,
            // on utilise les valeurs existantes
            if (candidature.getDateCandidature() == null) {
                candidature.setDateCandidature(existingCandidature.getDateCandidature());
            }
            if (candidature.getDocuments() == null) {
                candidature.setDocuments(existingCandidature.getDocuments());
            }

            String sql = "UPDATE candidature SET date_candidature = ?, statut_candidature = ?, " +
                    "note_candidature = ?, documents_candidature = ? " +
                    "WHERE id_annonce = ? AND id_demandeurs = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDate(1, new java.sql.Date(candidature.getDateCandidature().getTime()));
            stmt.setString(2, candidature.getStatut());
            stmt.setInt(3, candidature.getNote());
            stmt.setString(4, candidature.getDocuments());
            stmt.setInt(5, candidature.getIdAnnonce());
            stmt.setInt(6, candidature.getIdDemandeur());

            int result = stmt.executeUpdate();
            conn.commit();
            return result > 0;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Supprime une candidature de la base de données
     * @param idAnnonce L'ID de l'annonce
     * @param idDemandeurs L'ID du demandeur
     * @return true si la suppression a réussi, false sinon
     * @throws SQLException En cas d'erreur SQL
     */
    public boolean deleteCandidature(int idAnnonce, int idDemandeurs) throws SQLException {
        Connection conn = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            String sql = "DELETE FROM candidature WHERE id_annonce = ? AND id_demandeurs = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idAnnonce);
            stmt.setInt(2, idDemandeurs);
            System.out.println(idAnnonce + " demandeur : " + idDemandeurs);

            int result = stmt.executeUpdate();
            conn.commit();
            return result > 0;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Récupère une candidature spécifique par ses IDs
     * @param idAnnonce L'ID de l'annonce
     * @param idDemandeurs L'ID du demandeur
     * @return La candidature trouvée ou null si aucune correspondance
     */
    public Candidature getCandidature(int idAnnonce, int idDemandeurs) {
        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM candidature WHERE id_annonce = ? AND id_demandeurs = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
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

    /**
     * Récupère toutes les candidatures d'un demandeur
     * @param idDemandeurs L'ID du demandeur
     * @return Liste des candidatures du demandeur
     */
    public List<Candidature> getCandidaturesByIdDemandeur(int idDemandeurs) {
        List<Candidature> candidatures = new ArrayList<>();
        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM candidature WHERE id_demandeurs = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
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

    /**
     * Récupère des informations sur les annonces associées aux candidatures d'un demandeur
     * @param idDemandeur L'ID du demandeur
     * @return Liste des informations sur les annonces
     */
    public List<String[]> getInfosAnnoncesCandidature(int idDemandeur) {
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

    /**
     * Récupère toutes les candidatures
     * @return Liste de toutes les candidatures
     * @throws SQLException En cas d'erreur SQL
     */
    public List<Candidature> getAllCandidatures() throws SQLException {
        List<Candidature> candidatures = new ArrayList<>();

        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM candidature";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

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
            throw e;
        }

        return candidatures;
    }

    /**
     * Vérifie si une candidature existe déjà
     * @param idDemandeur L'ID du demandeur
     * @param idAnnonce L'ID de l'annonce
     * @return true si la candidature existe, false sinon
     * @throws SQLException En cas d'erreur SQL
     */
    public boolean existeCandidature(int idDemandeur, int idAnnonce) throws SQLException {
        try (Connection conn = getConnection()) {
            String sql = "SELECT COUNT(*) FROM candidature WHERE id_annonce = ? AND id_demandeurs = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idAnnonce);
            stmt.setInt(2, idDemandeur);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return false;
    }
}