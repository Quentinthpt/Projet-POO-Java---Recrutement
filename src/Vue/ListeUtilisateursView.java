package Vue;

import DAO.UtilisateurDAOImpl;
import Modele.DemandeurEmploi;
import Modele.Utilisateur;
import Vue.Components.FooterComponent;
import Vue.Components.HeaderComponent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ListeUtilisateursView extends JFrame {
    private JTable utilisateursTable;
    private DefaultTableModel tableModel;

    public ListeUtilisateursView() {
        setTitle("MatchaJob - Liste des utilisateurs");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Couleurs
        Color bleuFonce = new Color(9, 18, 66);
        Color bleuClair = new Color(45, 132, 255);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Ajout des composants
        mainPanel.add(new HeaderComponent(this), BorderLayout.NORTH);
        mainPanel.add(createContentPanel(bleuFonce, bleuClair), BorderLayout.CENTER);
        mainPanel.add(new FooterComponent(), BorderLayout.SOUTH);

        // Configuration du défilement
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        configureScrollPane(scrollPane);

        add(scrollPane);
        setVisible(true);
    }

    private JPanel createContentPanel(Color bleuFonce, Color bleuClair) {
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Titre
        JLabel titre = new JLabel("Liste des utilisateurs", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 24));
        titre.setForeground(bleuFonce);
        contentPanel.add(titre, BorderLayout.NORTH);

        // Tableau des utilisateurs
        initializeTableModel();
        configureTableAppearance(bleuFonce, bleuClair);

        // Boutons
        JPanel buttonPanel = createButtonPanel(bleuFonce, bleuClair);

        // Assemblage
        contentPanel.add(new JScrollPane(utilisateursTable), BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Chargement des données
        loadUtilisateurs();

        return contentPanel;
    }

    private void initializeTableModel() {
        // Ajout de la colonne "Statut" pour les demandeurs
        String[] columnNames = {"Type", "Nom", "Prénom", "Email", "Rôle", "Âge", "Expérience", "CV", "Statut"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        utilisateursTable = new JTable(tableModel);
    }

    private void configureTableAppearance(Color bleuFonce, Color bleuClair) {
        utilisateursTable.setRowHeight(30);
        utilisateursTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        utilisateursTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        utilisateursTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        utilisateursTable.setIntercellSpacing(new Dimension(0, 0));
        utilisateursTable.setShowGrid(false);
        utilisateursTable.setSelectionBackground(bleuClair);
        utilisateursTable.setSelectionForeground(Color.WHITE);
    }

    private JPanel createButtonPanel(Color bleuFonce, Color bleuClair) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton refreshButton = createStyledButton("Actualiser", bleuFonce, e -> loadUtilisateurs());
        JButton supprimerButton = createStyledButton("Supprimer", new Color(200, 50, 50), e -> supprimerUtilisateur());
        //JButton modifierStatutButton = createStyledButton("Modifier le statut", new Color(50, 150, 50), e -> modifierStatutDemandeur());

        buttonPanel.add(refreshButton);
        buttonPanel.add(supprimerButton);
        //buttonPanel.add(modifierStatutButton);

        return buttonPanel;
    }

    private JButton createStyledButton(String text, Color bgColor, java.awt.event.ActionListener action) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.addActionListener(action);
        return button;
    }

    private void configureScrollPane(JScrollPane scrollPane) {
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setBackground(Color.WHITE);
        verticalScrollBar.setForeground(new Color(45, 132, 255));
        verticalScrollBar.setUnitIncrement(16);
        verticalScrollBar.setPreferredSize(new Dimension(10, 0));
    }

    private void loadUtilisateurs() {
        try {
            UtilisateurDAOImpl dao = new UtilisateurDAOImpl();

            // Vider le tableau
            tableModel.setRowCount(0);

            // Charger les administrateurs
            List<Utilisateur> admins = dao.getAllAdmins();
            for (Utilisateur admin : admins) {
                Object[] rowData = {
                        "Admin",
                        admin.getNom(),
                        admin.getPrenom(),
                        admin.getEmail(),
                        "Admin",
                        "-", "-", "-", "-"  // Ajout d'un "-" pour la colonne statut
                };
                tableModel.addRow(rowData);
            }

            // Charger les demandeurs
            List<DemandeurEmploi> demandeurs = dao.getAllDemandeurs();
            for (DemandeurEmploi demandeur : demandeurs) {
                Object[] rowData = {
                        "Demandeur",
                        demandeur.getNom(),
                        demandeur.getPrenom(),
                        demandeur.getEmail(),
                        demandeur.getRole(),
                        demandeur.getAge(),
                        demandeur.getExperience(),
                        demandeur.getCv(),
                        demandeur.getStatut() != null ? demandeur.getStatut() : "En attente" // Affichage du statut
                };
                tableModel.addRow(rowData);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Erreur lors du chargement des utilisateurs: " + e.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void supprimerUtilisateur() {
        int selectedRow = utilisateursTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez sélectionner un utilisateur à supprimer",
                    "Aucune sélection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String type = (String) tableModel.getValueAt(selectedRow, 0);
        String email = (String) tableModel.getValueAt(selectedRow, 3);

        int option = JOptionPane.showConfirmDialog(this,
                "Voulez-vous vraiment supprimer cet utilisateur ?\nEmail: " + email,
                "Confirmation de suppression",
                JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            try {
                UtilisateurDAOImpl dao = new UtilisateurDAOImpl();
                boolean success = false;

                if ("Admin".equals(type)) {
                    success = dao.supprimerAdmin(email);
                } else {
                    success = dao.supprimerDemandeur(email);
                }

                if (success) {
                    JOptionPane.showMessageDialog(this,
                            "Utilisateur supprimé avec succès!",
                            "Succès",
                            JOptionPane.INFORMATION_MESSAGE);
                    loadUtilisateurs(); // Rafraîchir la liste
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Échec de la suppression de l'utilisateur",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this,
                        "Erreur lors de la suppression: " + e.getMessage(),
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    private void modifierStatutDemandeur() {
        int selectedRow = utilisateursTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez sélectionner un demandeur d'emploi",
                    "Aucune sélection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String type = (String) tableModel.getValueAt(selectedRow, 0);
        if (!"Demandeur".equals(type)) {
            JOptionPane.showMessageDialog(this,
                    "Vous ne pouvez modifier que le statut des demandeurs d'emploi",
                    "Sélection incorrecte",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String email = (String) tableModel.getValueAt(selectedRow, 3);
        String statutActuel = (String) tableModel.getValueAt(selectedRow, 8);

        // Options de statut possibles
        String[] options = {"En attente", "Accepté", "Refusé", "En cours de traitement"};

        String nouveauStatut = (String) JOptionPane.showInputDialog(this,
                "Choisissez le nouveau statut pour le demandeur :\nEmail: " + email,
                "Modification du statut",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                statutActuel);

        if (nouveauStatut != null && !nouveauStatut.equals(statutActuel)) {
            try {
                UtilisateurDAOImpl dao = new UtilisateurDAOImpl();
                boolean success = dao.modifierStatutDemandeur(email, nouveauStatut);

                if (success) {
                    JOptionPane.showMessageDialog(this,
                            "Statut modifié avec succès!",
                            "Succès",
                            JOptionPane.INFORMATION_MESSAGE);
                    loadUtilisateurs(); // Rafraîchir la liste
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Échec de la modification du statut",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this,
                        "Erreur lors de la modification du statut: " + e.getMessage(),
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
}