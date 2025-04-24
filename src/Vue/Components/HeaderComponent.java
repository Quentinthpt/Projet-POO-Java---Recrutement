package Vue.Components;

import DAO.EmployeurDAOImpl;
import DAO.NotificationDAO;
import DAO.NotificationDAOImpl;
import Modele.Notification;
import Modele.SessionUtilisateur;
import Vue.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HeaderComponent extends JPanel {

    public HeaderComponent(JFrame parentFrame) {
        // Mise à jour de la référence du frame courant
        // Couleurs
        Color bleuFonce = new Color(9, 18, 66);
        Color bleuClair = new Color(45, 132, 255);
        Color blanc = Color.WHITE;

        setLayout(new BorderLayout());
        setBackground(blanc);
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Logo
        ImageIcon logoIcon = new ImageIcon("src/images/telechargement2.png");
        Image scaledLogo = logoIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(scaledLogo);
        JLabel logoLabel = new JLabel(" MatchaJob", logoIcon, JLabel.LEFT);
        logoLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        logoLabel.setForeground(bleuFonce);
        logoLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logoLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent e) {
                parentFrame.dispose();
                new MainPage();
            }
        });

        // Menu central
        JPanel menuPanel = createMenuPanel(bleuFonce, bleuClair, parentFrame);

        // Menu droit
        JPanel rightMenu = createRightMenu(bleuFonce, blanc, parentFrame);

        add(logoLabel, BorderLayout.WEST);
        add(menuPanel, BorderLayout.CENTER);
        add(rightMenu, BorderLayout.EAST);
    }

    private JPanel createMenuPanel(Color bleuFonce, Color bleuClair, JFrame parentFrame) {
        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 5));
        menuPanel.setBackground(Color.WHITE);

        if (SessionUtilisateur.getInstance().getRole() == "Admin") {
            String[] menuItems = {"trouver un emploi", "Consulter Candidats", "recruteurs"};
            for (String item : menuItems) {
                JLabel label = new JLabel(item);
                label.setFont(new Font("SansSerif", Font.PLAIN, 16));
                label.setForeground(bleuFonce);
                label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                if (item.equals("trouver un emploi")) {
                    label.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mousePressed(java.awt.event.MouseEvent evt) {
                            parentFrame.dispose();
                            new OffreEmploiView();
                        }
                        public void mouseEntered(java.awt.event.MouseEvent evt) {
                            label.setForeground(bleuClair);
                        }
                        public void mouseExited(java.awt.event.MouseEvent evt) {
                            label.setForeground(bleuFonce);
                        }
                    });
                }
                if (item.equals("Consulter Candidats")) {
                    label.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mousePressed(java.awt.event.MouseEvent evt) {
                            parentFrame.dispose();
                            new ListeUtilisateursView();
                        }
                        public void mouseEntered(java.awt.event.MouseEvent evt) {
                            label.setForeground(bleuClair);
                        }
                        public void mouseExited(java.awt.event.MouseEvent evt) {
                            label.setForeground(bleuFonce);
                        }
                    });
                }

                if (item.equals("recruteurs")) {
                    label.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mousePressed(java.awt.event.MouseEvent evt) {
                            parentFrame.dispose();
                            new EntreprisePage(new EmployeurDAOImpl(
                                    "jdbc:mysql://localhost:3306/recrutement",
                                    "root",
                                    ""
                            )).show();
                        }

                        public void mouseEntered(java.awt.event.MouseEvent evt) {
                            label.setForeground(bleuClair);
                        }
                        public void mouseExited(java.awt.event.MouseEvent evt) {
                            label.setForeground(bleuFonce);
                        }
                    });
                }
                menuPanel.add(label);
            }
            return menuPanel;
            } else {
            String[] menuItems = {"Trouver un emploi", "Mes Candidatures", "Recruteurs"};
            for (String item : menuItems) {
                JLabel label = new JLabel(item);
                label.setFont(new Font("SansSerif", Font.PLAIN, 16));
                label.setForeground(bleuFonce);
                label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                if (item.equals("Trouver un emploi")) {
                    label.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mousePressed(java.awt.event.MouseEvent evt) {
                            parentFrame.dispose();
                            new OffreEmploiView();
                        }
                        public void mouseEntered(java.awt.event.MouseEvent evt) {
                            label.setForeground(bleuClair);
                        }
                        public void mouseExited(java.awt.event.MouseEvent evt) {
                            label.setForeground(bleuFonce);
                        }
                    });
                }
                if (item.equals("Mes Candidatures")) {
                    if(SessionUtilisateur.getInstance().getEmail() != null){
                        label.addMouseListener(new java.awt.event.MouseAdapter() {
                            public void mousePressed(java.awt.event.MouseEvent evt) {
                                parentFrame.dispose();
                                new CandidatureView();
                            }
                            public void mouseEntered(java.awt.event.MouseEvent evt) {
                                label.setForeground(bleuClair);
                            }
                            public void mouseExited(java.awt.event.MouseEvent evt) {
                                label.setForeground(bleuFonce);
                            }
                        });
                    }
                }

                if (item.equals("Recruteurs")) {
                    label.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mousePressed(java.awt.event.MouseEvent evt) {
                            parentFrame.dispose();
                            new EntreprisePage(new EmployeurDAOImpl(
                                "jdbc:mysql://localhost:3306/recrutement",
                                "root",
                                ""
                            )).show();
                        }

                        public void mouseEntered(java.awt.event.MouseEvent evt) {
                            label.setForeground(bleuClair);
                        }
                        public void mouseExited(java.awt.event.MouseEvent evt) {
                            label.setForeground(bleuFonce);
                        }
                    });
                }
                menuPanel.add(label);
            }
            return menuPanel;
        }
    }

    private JPanel createRightMenu(Color bleuFonce, Color blanc, JFrame parentFrame) {
        JPanel rightMenu = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 5));
        rightMenu.setBackground(blanc);

        if (SessionUtilisateur.getInstance().getEmail() == null) {
            JLabel monCompteLabel = new JLabel("👤 Se Connecter ?");
            monCompteLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            JPopupMenu popupMenu = new JPopupMenu();
            JMenuItem menuConnexion = new JMenuItem("Connexion");
            JMenuItem menuInscription = new JMenuItem("Inscription");

            menuConnexion.addActionListener(e -> {
                // Assurer que la navigation fonctionne depuis le menu
                parentFrame.dispose();
                LoginView login = new LoginView("connexion");
                login.setVisible(true);  // <--- ici
            });
            menuInscription.addActionListener(e -> {
                // Assurer que la navigation fonctionne depuis le menu
                parentFrame.dispose();
                LoginView login = new LoginView("inscription");
                login.setVisible(true);  // <--- ici
            });

            popupMenu.add(menuConnexion);
            popupMenu.add(menuInscription);

            monCompteLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mousePressed(java.awt.event.MouseEvent evt) {
                    popupMenu.show(monCompteLabel, evt.getX(), evt.getY());
                }
            });

            rightMenu.add(new JLabel("♡ 0"));

            rightMenu.add(monCompteLabel);
        } else {
            JLabel monCompte = new JLabel("👤 Mon compte");
            monCompte.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            JPopupMenu compteMenu = new JPopupMenu();
            JMenuItem profil = new JMenuItem("Mon profil");
            JMenuItem candidatures = new JMenuItem("Mes candidatures");
            JMenuItem deconnexion = new JMenuItem("Déconnexion");

            profil.addActionListener(ev -> {
                // Assurer que la navigation fonctionne depuis le menu
                parentFrame.dispose();
                new ProfilPage();
            });
            candidatures.addActionListener(ev -> {
                // Assurer que la navigation fonctionne depuis le menu
                parentFrame.dispose();
                new CandidatureView();
            });
            deconnexion.addActionListener(ev -> {
                SessionUtilisateur.getInstance().clearSession();
                // Assurer que la navigation fonctionne depuis le menu
                SessionUtilisateur.getInstance().clearSession();
                parentFrame.dispose();
                new MainPage();
            });

            compteMenu.add(profil);
            compteMenu.add(candidatures);
            compteMenu.add(deconnexion);

            monCompte.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mousePressed(java.awt.event.MouseEvent e) {
                    compteMenu.show(monCompte, e.getX(), e.getY());
                }
            });


            // Charger le nombre de notifications non lues
            NotificationDAOImpl notifDAO = new NotificationDAOImpl();
            int notifCount = notifDAO.countNotificationsNonLues(SessionUtilisateur.getInstance().getId());

// Déterminer l'icône du cœur (plein ou vide)
            String coeur = notifCount > 0 ? "♥" : "♡";
            JLabel notifLabel = new JLabel(coeur + " " + notifCount);
            notifLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            notifLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
            notifLabel.setToolTipText("Voir mes notifications");

// Action quand on clique sur le cœur
            notifLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mousePressed(java.awt.event.MouseEvent e) {
                    // Ouvrir la fenêtre de notification
                    new NotificationView();

                    // Après lecture, remettre à zéro
                    notifLabel.setText("♡ 0");
                }
            });

// Ajouter au menu
            rightMenu.add(notifLabel);



            //rightMenu.add(new JLabel("♡ 0"));
            rightMenu.add(monCompte);
        }
        return rightMenu;
    }

    private int getNbNotifNonLues() {
        NotificationDAOImpl dao = new NotificationDAOImpl();
        return dao.getNotificationsNonLues(SessionUtilisateur.getInstance().getId()).size();
    }

    private void afficherNotifications(JFrame parentFrame) {
        NotificationDAOImpl dao = new NotificationDAOImpl();
        List<Notification> notifs = dao.getNotificationsNonLues(SessionUtilisateur.getInstance().getId());

        if (notifs.isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "Aucune nouvelle notification.", "Notifications", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder msg = new StringBuilder("Notifications :\n\n");
            for (Notification n : notifs) {
                msg.append("• ").append(n.getMessage()).append("\n");
            }

            JOptionPane.showMessageDialog(parentFrame, msg.toString(), "Notifications", JOptionPane.INFORMATION_MESSAGE);
            dao.marquerCommeLues(SessionUtilisateur.getInstance().getId());
        }
    }

}