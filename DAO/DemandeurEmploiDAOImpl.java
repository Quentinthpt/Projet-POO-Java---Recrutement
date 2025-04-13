package DAO;

import Modele.DemandeurEmploi;

import java.sql.*;

import static java.sql.DriverManager.getConnection;

public class DemandeurEmploiDAOImpl {
    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/recrutement";
        String user = "root";
        String password = "";
        return DriverManager.getConnection(url, user, password);
    }

    public DemandeurEmploi getDemandeurByEmail(String email) {
        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM demandeurs WHERE e_mail_demandeur=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new DemandeurEmploi(
                        rs.getString("nom_demandeur"),
                        rs.getString("prenom_demandeur"),
                        rs.getInt("age_demandeur"),
                        rs.getString("e_mail_demandeur"),
                        rs.getString("adresse_demandeur"),
                        rs.getString("experience_demandeur"),
                        rs.getString("cv_demandeur"),
                        rs.getString("pw_demandeur"),
                        "demandeur"
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
