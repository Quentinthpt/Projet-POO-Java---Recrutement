package Vue.Components;

import DAO.EmployeurDAOImpl;
import Modele.SessionUtilisateur;
import Vue.*;

import javax.swing.*;
import java.awt.*;

public class HeaderComponent extends JPanel {
    public HeaderComponent(JFrame parentFrame) {
        // Couleurs
        Color bleuFonce = new Color(9, 18, 66);
        Color bleuClair = new Color(45, 132, 255);
        Color blanc = Color.WHITE;

        setLayout(new BorderLayout());
        setBackground(blanc);
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Logo
        ImageIcon logoIcon = new ImageIcon("images/telechargement2.png");
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

        String[] menuItems = {"trouver un emploi", "candidats", "recruteurs"};
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
            if (item.equals("candidats")) {
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

            if (item.equals("recruteurs")) {
                JPopupMenu recruteursMenu = new JPopupMenu();
                JMenuItem menuRecruteurs = new JMenuItem("Recruteurs");
                JMenuItem menuEntreprises = new JMenuItem("Entreprises");

                menuRecruteurs.addActionListener(e -> {
                    parentFrame.dispose();
                    new RecruteurPage(new EmployeurDAOImpl(
                        "jdbc:mysql://localhost:3306/recrutement",
                        "root",
                        ""
                    )).show();
                });

                menuEntreprises.addActionListener(e -> {
                    parentFrame.dispose();
                    new EntreprisePage(new EmployeurDAOImpl(
                        "jdbc:mysql://localhost:3306/recrutement",
                        "root",
                        ""
                    )).show();
                });

                recruteursMenu.add(menuRecruteurs);
                recruteursMenu.add(menuEntreprises);

                label.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mousePressed(java.awt.event.MouseEvent evt) {
                        recruteursMenu.show(label, 0, label.getHeight());
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
                parentFrame.dispose();
                LoginView login = new LoginView("connexion");
                login.setVisible(true);  // <--- ici
            });
            menuInscription.addActionListener(e -> {
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
                parentFrame.dispose();
                new ProfilPage();
            });
            candidatures.addActionListener(ev -> {
                parentFrame.dispose();
                new CandidatureView();
            });
            deconnexion.addActionListener(ev -> {
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

            rightMenu.add(new JLabel("♡ 0"));
            rightMenu.add(monCompte);
        }
        return rightMenu;
    }
}