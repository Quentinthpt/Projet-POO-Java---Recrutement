package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Modele.Notification;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class NotificationDAOImpl {
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/recrutement",
                "root",
                ""
        );
    }

    public void ajouterNotification(int idUtilisateur, String message) {
        String sql = "INSERT INTO notification (id_utilisateur, message) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUtilisateur);
            stmt.setString(2, message);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Notification> getNotificationsNonLues(int idUtilisateur) {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT * FROM notification WHERE id_utilisateur = ? AND lu = false ORDER BY date_notification DESC";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUtilisateur);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Notification notif = new Notification();
                notif.setId(rs.getInt("id"));
                notif.setId_user(rs.getInt("id_utilisateur"));
                notif.setMessage(rs.getString("message"));
                notif.setLu(rs.getBoolean("lu"));
                notif.setDate(rs.getTimestamp("date_notification"));
                list.add(notif);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void marquerCommeLues(int idUtilisateur) {
        String sql = "UPDATE notification SET lu = true WHERE id_utilisateur = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUtilisateur);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void marquerNotificationsCommeLues(int idUtilisateur) {
        String sql = "UPDATE notification SET lu = 1 WHERE id_utilisateur = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUtilisateur);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Integer> getAllAdminIds() {
        List<Integer> ids = new ArrayList<>();
        String sql = "SELECT id_admin FROM administrateurs";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ids.add(rs.getInt("id_admin"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ids;
    }

    public int countNotificationsNonLues(int idUtilisateur) {
        String sql = "SELECT COUNT(*) FROM notification WHERE id_utilisateur = ? AND lu = false";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUtilisateur);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
