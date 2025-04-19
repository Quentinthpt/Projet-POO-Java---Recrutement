package DAO;

import Modele.Notification;

import java.util.List;

public interface NotificationDAO {
    public void ajouterNotification(int idUtilisateur, String message);
    public void envoyerNotification(int idUtilisateur, String message);
    public List<Notification> getNotificationsNonLues(int idUtilisateur);
    public void marquerCommeLues(int idUtilisateur);

    public void marquerNotificationsCommeLues(int idUtilisateur);
    public List<Integer> getAllAdminIds();
    public int countNotificationsNonLues(int idUtilisateur);
    public void marquerToutCommeLu(int idUtilisateur);

}
