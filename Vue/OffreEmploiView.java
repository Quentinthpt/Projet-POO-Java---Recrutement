package Vue;

import DAO.OffreEmploiDAOImpl;
import Modele.Annonce;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

public class OffreEmploiView extends JFrame {
    private JTable annoncesTable;
    private DefaultTableModel tableModel;

    public OffreEmploiView() {
        // Configuration de base
        setTitle("MatchaJob - Offres d'emploi");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Couleurs
        Color bleuFonce = new Color(9, 18, 66);
        Color bleuClair = new Color(45, 132, 255);
        Color blanc = Color.WHITE;
        Color fondBas = new Color(240, 240, 240);

        // ---------- Structure principale ----------
        JPanel mainPanel = new JPanel(new BorderLayout());

        // 1. Barre de navigation
        mainPanel.add(createTopNav(bleuFonce, bleuClair, blanc), BorderLayout.NORTH);

        // 2. Contenu central
        mainPanel.add(createContentPanel(bleuFonce, bleuClair), BorderLayout.CENTER);

        // 3. Footer
        mainPanel.add(createFooter(fondBas), BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    // ---------- Méthodes de création des composants ----------

    private JPanel createTopNav(Color bleuFonce, Color bleuClair, Color blanc) {
        JPanel topNav = new JPanel(new BorderLayout());
        topNav.setBackground(blanc);
        topNav.setBorder(new EmptyBorder(10, 20, 10, 20));

        // Logo cliquable
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

        // Menu compte
        JPanel rightMenu = createAccountMenu(blanc, bleuFonce);

        topNav.add(logoLabel, BorderLayout.WEST);
        topNav.add(menuPanel, BorderLayout.CENTER);
        topNav.add(rightMenu, BorderLayout.EAST);

        return topNav;
    }

    private JPanel createAccountMenu(Color blanc, Color bleuFonce) {
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

        return rightMenu;
    }

    private JPanel createContentPanel(Color bleuFonce, Color bleuClair) {
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Titre
        JLabel titre = new JLabel("Nos offres d'emploi", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 24));
        titre.setForeground(bleuFonce);
        contentPanel.add(titre, BorderLayout.NORTH);

        // Tableau des annonces
        String[] columnNames = {"Titre", "Description", "Salaire", "Lieu", "Type de contrat", "Expérience requise", "Date début"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        annoncesTable = new JTable(tableModel);
        stylizeTable(bleuFonce, bleuClair);

        // Boutons
        JPanel buttonPanel = createButtonPanel(bleuFonce, bleuClair);

        // Assemblage
        contentPanel.add(new JScrollPane(annoncesTable), BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Chargement des données
        loadAnnonces();

        return contentPanel;
    }

    private void stylizeTable(Color bleuFonce, Color bleuClair) {
        annoncesTable.setRowHeight(30);
        annoncesTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        annoncesTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        annoncesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        annoncesTable.setIntercellSpacing(new Dimension(0, 0));
        annoncesTable.setShowGrid(false);

        // Surlignage en bleu clair
        annoncesTable.setSelectionBackground(bleuClair);
        annoncesTable.setSelectionForeground(Color.WHITE);
    }

    private JPanel createButtonPanel(Color bleuFonce, Color bleuClair) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton postulerButton = new JButton("Postuler");
        styleButton(postulerButton, bleuClair);
        postulerButton.addActionListener(e -> postulerAction());

        JButton refreshButton = new JButton("Actualiser");
        styleButton(refreshButton, bleuFonce);
        refreshButton.addActionListener(e -> loadAnnonces());

        buttonPanel.add(postulerButton);
        buttonPanel.add(refreshButton);

        return buttonPanel;
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    private JPanel createFooter(Color fondBas) {
        JPanel footerPanel = new JPanel(new BorderLayout());

        String text = "MatchaJob, Un job à ton goût… vertueux comme du matcha\n\n" +
                "Contact: \n" +
                "10 rue Sextius Michel, 75010 Paris, France\n" +
                "Mail: contact@matchajob.com\n" +
                "Téléphone: +33 (0) 6 15 08 75 05\n";

        JTextArea texteBasPage = new JTextArea(text);
        texteBasPage.setEditable(false);
        texteBasPage.setBackground(fondBas);
        texteBasPage.setLineWrap(true);
        texteBasPage.setWrapStyleWord(true);
        texteBasPage.setFocusable(false);
        texteBasPage.setBorder(new EmptyBorder(10, 20, 10, 20));
        texteBasPage.setFont(new Font("SansSerif", Font.PLAIN, 10));

        footerPanel.add(texteBasPage, BorderLayout.CENTER);
        return footerPanel;
    }

    // ---------- Méthodes fonctionnelles ----------

    private void loadAnnonces() {
        try {
            OffreEmploiDAOImpl dao = new OffreEmploiDAOImpl();
            List<Annonce> annonces = dao.getAllAnnonces();

            tableModel.setRowCount(0); // Vider le tableau

            for (Annonce annonce : annonces) {
                Object[] rowData = {
                        annonce.getTitre(),
                        formatDescription(annonce.getDescription()),
                        annonce.getSalaire() + " €",
                        annonce.getLieuTravail(),
                        annonce.getTypeContrat(),
                        annonce.getExperienceRequise(),
                        annonce.getDateDebut()
                };
                tableModel.addRow(rowData);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Erreur lors du chargement des annonces: " + e.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private String formatDescription(String description) {
        if (description.length() > 50) {
            return description.substring(0, 47) + "...";
        }
        return description;
    }

    private void postulerAction() {
        int selectedRow = annoncesTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez sélectionner une offre à laquelle postuler",
                    "Aucune sélection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String titreOffre = (String) tableModel.getValueAt(selectedRow, 0);
        int option = JOptionPane.showConfirmDialog(this,
                "Voulez-vous postuler à l'offre : " + titreOffre + "?",
                "Confirmation de postulation",
                JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            // Logique de postulation à implémenter
            JOptionPane.showMessageDialog(this,
                    "Votre candidature a été envoyée avec succès!",
                    "Succès",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void handleNavigation(String item) {
        dispose();

        switch(item.toLowerCase()) {
            case "trouver un emploi":
                new OffreEmploiView().setVisible(true);
                break;
            case "candidats":
                // new CandidatsView().setVisible(true);
                break;
            case "recruteurs":
                // new RecruteursView().setVisible(true);
                break;
            default:
                new MainPage().setVisible(true);
        }
    }
}