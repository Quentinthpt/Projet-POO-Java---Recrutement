package Vue;

//importer les bibliothèques + fichiers
import DAO.NotificationDAOImpl;
import Modele.Notification;
import Modele.SessionUtilisateur;

import javax.swing.*;
import java.awt.*;
import java.util.List;

//création de la page de notification
public class NotificationView extends JFrame {
    public NotificationView() {
        //nouvelle dimension pour cette page
        setTitle("Mes notifications");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Color bleuFonce = new Color(9, 18, 66);
        Color blanc = Color.WHITE;

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(blanc);

        JLabel title = new JLabel("📩 Mes Notifications", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(bleuFonce);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panel.add(title, BorderLayout.NORTH);

        //récupération des notifications non lues dans la bdd
        NotificationDAOImpl dao = new NotificationDAOImpl();
        List<Notification> notifications = dao.getNotificationsNonLues(SessionUtilisateur.getInstance().getId());

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Notification notif : notifications) {
            listModel.addElement(notif.getMessage());
        }

        JList<String> notifList = new JList<>(listModel);
        notifList.setFont(new Font("SansSerif", Font.PLAIN, 14));
        notifList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        notifList.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        JScrollPane scrollPane = new JScrollPane(notifList);

        panel.add(scrollPane, BorderLayout.CENTER);

        //marquer les notifications comme lues
        dao.marquerNotificationsCommeLues(SessionUtilisateur.getInstance().getId());

        add(panel);
        setVisible(true);
    }
}
