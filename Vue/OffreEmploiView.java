package Vue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class OffreEmploiView extends JFrame {
    public OffreEmploiView() {
        setTitle("MatchaJob - Offres d'emploi");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Couleurs (identiques à MainPage)
        Color bleuFonce = new Color(9, 18, 66);
        Color bleuClair = new Color(45, 132, 255);
        Color blanc = Color.WHITE;

        // ---------- Barre de navigation (identique à MainPage) ----------
        JPanel topNav = new JPanel(new BorderLayout());
        topNav.setBackground(blanc);
        topNav.setBorder(new EmptyBorder(10, 20, 10, 20));

        // Logo
        ImageIcon logoIcon = new ImageIcon("images/telechargement2.png");
        Image scaledLogo = logoIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(scaledLogo);

        JLabel logoLabel = new JLabel(" MatchaJob", logoIcon, JLabel.LEFT);
        logoLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        logoLabel.setForeground(bleuFonce);
        logoLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logoLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new MainPage().setVisible(true);
                dispose();
            }
        });

        // Menu central
        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 5));
        menuPanel.setBackground(blanc);

        String[] menuItems = {"trouver un emploi", "candidats", "recruteurs", "nos agences", "à propos"};
        for (String item : menuItems) {
            JLabel linkLabel = new JLabel("<html><u>" + item + "</u></html>");
            linkLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
            linkLabel.setForeground(bleuFonce);
            linkLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            // Gestion des clics
            linkLabel.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    handleNavigation(item);
                }
                public void mouseEntered(MouseEvent e) {
                    linkLabel.setForeground(bleuClair);
                }
                public void mouseExited(MouseEvent e) {
                    linkLabel.setForeground(bleuFonce);
                }
            });

            menuPanel.add(linkLabel);
        }

        // Menu droit (compte)
        JPanel rightMenu = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 5));
        rightMenu.setBackground(blanc);

        JLabel monCompteLabel = new JLabel("👤 mon compte");
        monCompteLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem menuConnexion = new JMenuItem("Connexion");
        JMenuItem menuInscription = new JMenuItem("Inscription");
        popupMenu.add(menuConnexion);
        popupMenu.add(menuInscription);

        monCompteLabel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                popupMenu.show(monCompteLabel, evt.getX(), evt.getY());
            }
        });

        rightMenu.add(new JLabel("♡ 0"));
        rightMenu.add(monCompteLabel);

        topNav.add(logoLabel, BorderLayout.WEST);
        topNav.add(menuPanel, BorderLayout.CENTER);
        topNav.add(rightMenu, BorderLayout.EAST);

        // ---------- Contenu principal ----------
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Titre
        JLabel titre = new JLabel("Nos offres d'emploi", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 24));
        titre.setForeground(bleuFonce);
        contentPanel.add(titre, BorderLayout.NORTH);

        // Zone de contenu (à remplacer par ta liste d'offres)
        JTextArea placeholder = new JTextArea();
        placeholder.setText("Liste des offres à implémenter ici...\n\n"
                + "Cette zone sera remplacée par:\n"
                + "- Une JTable avec les offres\n"
                + "- Un système de filtres\n"
                + "- Des boutons de candidature");
        placeholder.setEditable(false);
        placeholder.setFont(new Font("SansSerif", Font.PLAIN, 16));
        contentPanel.add(new JScrollPane(placeholder), BorderLayout.CENTER);

        // ---------- Footer ----------
        Color fond_bas = new Color(240, 240, 240); // Couleur de fond du bas de page

        String text = "MatchaJob, Un job à ton goût… vertueux comme du matcha\n\n" +
                "Contact: \n" +
                "10 rue Sextius Michel, 75010 Paris, France\n" +
                "Mail: contact@matchajob.com\n" +
                "Téléphone: +33 (0) 6 15 08 75 05\n";
        JTextArea texte_bas_page = new JTextArea(text);
        texte_bas_page.setEditable(false);
        texte_bas_page.setBackground(fond_bas);
        texte_bas_page.setLineWrap(true);
        texte_bas_page.setWrapStyleWord(true);
        texte_bas_page.setFocusable(false);
        texte_bas_page.setBorder(new EmptyBorder(10, 20, 10, 20));
        texte_bas_page.setFont(new Font("SansSerif", Font.PLAIN, 10));

        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.add(texte_bas_page, BorderLayout.CENTER);

        // ---------- Assemblage final ----------
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(topNav, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH); // Ajout du footer ici

        add(mainPanel);
        setVisible(true);

    }

    private void handleNavigation(String item) {
        dispose(); // Ferme la fenêtre actuelle

        switch(item) {
            case "trouver un emploi":
                new OffreEmploiView().setVisible(true);
                break;
            case "candidats":
                // new CandidatsView().setVisible(true); // À implémenter
                break;
            // ... autres cas
            default:
                new MainPage().setVisible(true);
        }
    }

}