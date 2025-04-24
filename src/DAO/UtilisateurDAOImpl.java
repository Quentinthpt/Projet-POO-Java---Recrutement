package DAO;

import Modele.DemandeurEmploi;
import Modele.SessionUtilisateur;
import Modele.Utilisateur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDAOImpl implements UtilisateurDAO {
    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/recrutement";
        String user = "root";
        String password = "";
        return DriverManager.getConnection(url, user, password);
    }



    public Utilisateur connecter(String email, String password) throws SQLException {
        Utilisateur admin = connecterAdmin(email, password);
        if (admin != null){
            return admin;
        }
        return connecterDemandeur(email, password);
    }

    private Utilisateur connecterAdmin(String email, String password) throws SQLException{
        try(Connection con = getConnection()){
            String sql = "SELECT * FROM administrateurs WHERE e_mail_admin = ? AND pw_admin = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                Utilisateur admin =  new Utilisateur(rs.getString("nom_admin"), rs.getString("prenom_admin"), email, password, "Admin");
                admin.setId(rs.getInt("id_admin"));
                System.out.println(admin);
                SessionUtilisateur.getInstance().setId(rs.getInt("id_admin"));
                return admin;
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public boolean modifierStatutDemandeur(String email, String nouveauStatut) throws SQLException {
        String sql = "UPDATE demandeur_emploi SET statut = ? WHERE email = ?";

        try (Connection con = getConnection();
             java.sql.PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setString(1, nouveauStatut);
            statement.setString(2, email);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        }
    }

    private Utilisateur connecterDemandeur(String email, String password) throws SQLException{
        try(Connection con = getConnection()){
            String sql = "SELECT * FROM demandeurs WHERE e_mail_demandeur = ? AND pw_demandeur = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                DemandeurEmploi demandeur = new DemandeurEmploi(
                        rs.getString("nom_demandeur"),
                        rs.getString("prenom_demandeur"),
                        rs.getInt("age_demandeur"),
                        email,
                        rs.getString("adresse_demandeur"),
                        rs.getString("experience_demandeur"),
                        rs.getString("cv_demandeur"),
                        password,
                        "demandeur"
                );
                demandeur.setId(rs.getInt("id_demandeurs"));
                return demandeur;
                //return new Utilisateur(rs.getString("nom_demandeur"), rs.getString("prenom_demandeur"), email, password, "Demandeur");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean inscrireDemandeur(Utilisateur utilisateur) {
        try(Connection con = getConnection()){
            String sql = "INSERT INTO demandeurs (nom_demandeur, prenom_demandeur, age_demandeur, e_mail_demandeur, adresse_demandeur, experience_demandeur, cv_demandeur, pw_demandeur) VALUES (?,?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, utilisateur.getNom());
            ps.setString(2, utilisateur.getPrenom());
            ps.setInt(3, utilisateur.getAge());
            ps.setString(4, utilisateur.getEmail());
            ps.setString(5, utilisateur.getAdresse());
            ps.setString(6, utilisateur.getExperience());
            ps.setString(7, utilisateur.getCv());
            ps.setString(8, utilisateur.getMotDePasse());
            return ps.executeUpdate() > 0;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean mettreAJourProfil(SessionUtilisateur utilisateur) {
        if (utilisateur.getId() == 0) {
            System.out.println("ID invalide pour la mise à jour du profil.");
            return false;
        }
        try (Connection con = getConnection()) {
            String sql = "UPDATE demandeurs SET nom_demandeur=?, prenom_demandeur=?, age_demandeur=?, e_mail_demandeur=?, adresse_demandeur=?, experience_demandeur=?, cv_demandeur=? WHERE id_demandeurs=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, utilisateur.getNom());
            ps.setString(2, utilisateur.getPrenom());
            ps.setInt(3, utilisateur.getAge());
            ps.setString(4, utilisateur.getEmail());
            ps.setString(5, utilisateur.getAdresse());
            ps.setString(6, utilisateur.getExperience());
            ps.setString(7, utilisateur.getCv());
            ps.setInt(8, utilisateur.getId());

            System.out.println("ID reçu pour update: "+ utilisateur.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Utilisateur> getAllAdmins() throws SQLException {
        List<Utilisateur> admins = new ArrayList<>();
        String sql = "SELECT * FROM administrateurs";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Utilisateur admin = new Utilisateur(
                        rs.getString("nom_admin"),
                        rs.getString("prenom_admin"),
                        rs.getString("e_mail_admin"),
                        "", // On ne retourne pas le mot de passe
                        "Admin"
                );
                admins.add(admin);
            }
        }
        return admins;
    }

    public List<DemandeurEmploi> getAllDemandeurs() throws SQLException {
        List<DemandeurEmploi> demandeurs = new ArrayList<>();
        String sql = "SELECT * FROM demandeurs";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                DemandeurEmploi demandeur = new DemandeurEmploi(
                        rs.getInt("id_demandeurs"),
                        rs.getString("nom_demandeur"),
                        rs.getString("prenom_demandeur"),
                        rs.getInt("age_demandeur"),
                        rs.getString("e_mail_demandeur"),
                        rs.getString("adresse_demandeur"),
                        rs.getString("experience_demandeur"),
                        rs.getString("cv_demandeur"),
                        "", // On ne retourne pas le mot de passe
                        "Demandeur"
                );
                demandeurs.add(demandeur);
            }
        }
        return demandeurs;
    }

    public boolean supprimerAdmin(String email) throws SQLException {
        String sql = "DELETE FROM administrateurs WHERE e_mail_admin = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean supprimerDemandeur(String email) throws SQLException {
        Connection conn = null;
        PreparedStatement stmtCandidature = null;
        PreparedStatement stmtDemandeur = null;
        boolean success = false;

        try {
            conn = getConnection();
            conn.setAutoCommit(false); // Désactiver l'auto-commit pour permettre une transaction

            // 1. Récupérer l'ID du demandeur à partir de son email
            String sqlGetId = "SELECT id_demandeurs FROM demandeurs WHERE e_mail_demandeur = ?";
            PreparedStatement stmtGetId = conn.prepareStatement(sqlGetId);
            stmtGetId.setString(1, email);
            ResultSet rs = stmtGetId.executeQuery();

            if (rs.next()) {
                int idDemandeur = rs.getInt("id_demandeurs");

                // 2. Supprimer d'abord les candidatures associées
                String sqlCandidature = "DELETE FROM candidature WHERE id_demandeurs = ?";
                stmtCandidature = conn.prepareStatement(sqlCandidature);
                stmtCandidature.setInt(1, idDemandeur);
                stmtCandidature.executeUpdate();

                // 3. Ensuite supprimer le demandeur
                String sqlDemandeur = "DELETE FROM demandeurs WHERE id_demandeurs = ?";
                stmtDemandeur = conn.prepareStatement(sqlDemandeur);
                stmtDemandeur.setInt(1, idDemandeur);
                int rowsAffected = stmtDemandeur.executeUpdate();

                success = rowsAffected > 0;
                conn.commit(); // Valider la transaction
            }

            return success;
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Annuler la transaction en cas d'erreur
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e;
        } finally {
            // Fermer les ressources
            if (stmtCandidature != null) {
                stmtCandidature.close();
            }
            if (stmtDemandeur != null) {
                stmtDemandeur.close();
            }
            if (conn != null) {
                conn.setAutoCommit(true); // Réactiver l'auto-commit
                conn.close();
            }
        }
    }

}
