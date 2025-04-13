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
    public List<Candidature> getCandidaturesByEmail(String email) {
        List<Candidature> liste = new ArrayList<>();

        try (Connection conn = getConnection()) {
            String sql = "SELECT c.date_candidature, c.statut_candidature, a.titre_annonce " +
                    "FROM candidature c " +
                    "JOIN demandeurs d ON c.id_demandeurs = d.id_demandeurs " +
                    "JOIN annonce a ON c.id_annonce = a.id_annonce " +
                    "WHERE d.e_mail_demandeur = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Candidature c = new Candidature();
                c.setDateCandidature(rs.getDate("date_candidature"));
                c.setStatut(rs.getString("statut_candidature"));
                c.setCommentaire(rs.getString("titre_annonce")); // temporairement utilisé pour le poste
                liste.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return liste;
    }

}
