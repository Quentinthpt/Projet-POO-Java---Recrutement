package Vue;

import DAO.CandidatureDAOImpl;
import DAO.OffreEmploiDAOImpl;
import Modele.Candidature;
import Modele.Annonce;
import Vue.Components.FooterComponent;
import Vue.Components.HeaderComponent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class GestionCandidaturesView extends JFrame {
    private JTable candidaturesTable;
    private DefaultTableModel tableModel;
    private Color bleuFonce = new Color(9, 18, 66);
    private Color bleuClair = new Color(45, 132, 255);

    public GestionCandidaturesView() {
        setTitle("MatchaJob - Gestion des candidatures");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new HeaderComponent(this), BorderLayout.NORTH);
        mainPanel.add(createContentPanel(), BorderLayout.CENTER);
        mainPanel.add(new FooterComponent(), BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        configureScrollPane(scrollPane);

        add(scrollPane);
        setVisible(true);
    }

    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titre = new JLabel("Gestion des candidatures", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 24));
        titre.setForeground(bleuFonce);
        contentPanel.add(titre, BorderLayout.NORTH);

        initializeTableModel();
        configureTableAppearance();

        JPanel buttonPanel = createButtonPanel();

        contentPanel.add(new JScrollPane(candidaturesTable), BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        loadCandidatures();

        return contentPanel;
    }

    private void initializeTableModel() {
        String[] columnNames = {
                "ID", "Offre", "Candidat", "Date", "Statut", "Note", "Documents"
        };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4 || column == 5; // Seuls le statut et la note sont modifiables
            }
        };
        candidaturesTable = new JTable(tableModel);
    }

    private void configureTableAppearance() {
        candidaturesTable.setRowHeight(30);
        candidaturesTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        candidaturesTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        candidaturesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        candidaturesTable.setIntercellSpacing(new Dimension(0, 0));
        candidaturesTable.setShowGrid(false);
        candidaturesTable.setSelectionBackground(bleuClair);
        candidaturesTable.setSelectionForeground(Color.WHITE);
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton ajouterButton = createStyledButton("Ajouter", bleuClair, e -> ajouterCandidature());
        JButton modifierButton = createStyledButton("Modifier", bleuClair, e -> modifierCandidature());
        JButton supprimerButton = createStyledButton("Supprimer", bleuClair, e -> supprimerCandidature());
        JButton refreshButton = createStyledButton("Actualiser", bleuFonce, e -> loadCandidatures());

        buttonPanel.add(ajouterButton);
        buttonPanel.add(modifierButton);
        buttonPanel.add(supprimerButton);
        buttonPanel.add(refreshButton);

        return buttonPanel;
    }

    private JButton createStyledButton(String text, Color bgColor, ActionListener action) {
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
        verticalScrollBar.setForeground(bleuClair);
        verticalScrollBar.setUnitIncrement(16);
        verticalScrollBar.setPreferredSize(new Dimension(10, 0));
    }

    private void loadCandidatures() {
        try {
            CandidatureDAOImpl dao = new CandidatureDAOImpl();
            List<Candidature> candidatures = dao.getAllCandidatures();

            tableModel.setRowCount(0);

            for (Candidature candidature : candidatures) {
                Object[] rowData = {
                        candidature.getIdAnnonce(), // Utilisation de l'ID de l'annonce comme identifiant temporaire
                        getOffreTitle(candidature.getIdAnnonce()),
                        candidature.getIdDemandeur(),
                        candidature.getDateCandidature(),
                        candidature.getStatut(),
                        candidature.getNote(),
                        candidature.getDocuments()
                };
                tableModel.addRow(rowData);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Erreur lors du chargement des candidatures: " + e.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private String getOffreTitle(int idAnnonce) {
        try {
            OffreEmploiDAOImpl dao = new OffreEmploiDAOImpl();
            Annonce annonce = dao.getAnnonceById(idAnnonce);
            return annonce != null ? annonce.getTitre() : "Offre inconnue";
        } catch (SQLException e) {
            return "Erreur";
        }
    }

    private void ajouterCandidature() {
        // TODO: Implémenter l'ajout d'une nouvelle candidature
        JOptionPane.showMessageDialog(this,
                "Fonctionnalité d'ajout à implémenter",
                "Information",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void modifierCandidature() {
        int selectedRow = candidaturesTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez sélectionner une candidature à modifier",
                    "Aucune sélection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idAnnonce = (int) tableModel.getValueAt(selectedRow, 0);
        int idDemandeur = (int) tableModel.getValueAt(selectedRow, 2);
        String nouveauStatut = (String) tableModel.getValueAt(selectedRow, 4);
        int nouvelleNote = (int) tableModel.getValueAt(selectedRow, 5);

        try {
            CandidatureDAOImpl dao = new CandidatureDAOImpl();
            Candidature candidature = new Candidature();
            candidature.setIdAnnonce(idAnnonce);
            candidature.setIdDemandeur(idDemandeur);
            candidature.setStatut(nouveauStatut);
            candidature.setNote(nouvelleNote);
            
            if (dao.updateCandidature(candidature)) {
                JOptionPane.showMessageDialog(this,
                        "Candidature mise à jour avec succès",
                        "Succès",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Erreur lors de la mise à jour de la candidature",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Erreur lors de la mise à jour: " + e.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void supprimerCandidature() {
        int selectedRow = candidaturesTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez sélectionner une candidature à supprimer",
                    "Aucune sélection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idAnnonce = (int) tableModel.getValueAt(selectedRow, 0);
        String offre = (String) tableModel.getValueAt(selectedRow, 1);

        int option = JOptionPane.showConfirmDialog(this,
                "Voulez-vous vraiment supprimer la candidature pour l'offre : " + offre + "?",
                "Confirmation de suppression",
                JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            try {
                CandidatureDAOImpl dao = new CandidatureDAOImpl();
                if (dao.deleteCandidature(idAnnonce)) {
                    loadCandidatures();
                    JOptionPane.showMessageDialog(this,
                            "Candidature supprimée avec succès",
                            "Succès",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Erreur lors de la suppression de la candidature",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this,
                        "Erreur lors de la suppression: " + e.getMessage(),
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
} 