package DAO;

import Modele.Utilisateur;

import java.sql.*;

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
                return new Utilisateur(rs.getString("nom_admin"), rs.getString("prenom_admin"), email, password);
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    private Utilisateur connecterDemandeur(String email, String password) throws SQLException{
        try(Connection con = getConnection()){
            String sql = "SELECT * FROM demandeurs WHERE e_mail_demandeur = ? AND pw_demandeur = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return new Utilisateur(rs.getString("nom_demandeur"), rs.getString("prenom_demandeur"), email, password);
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

}
