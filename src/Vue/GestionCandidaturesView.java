package Vue;

import DAO.CandidatureDAOImpl;
import DAO.OffreEmploiDAOImpl;
import Modele.Candidature;
import Modele.Annonce;
import Modele.SessionUtilisateur;
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

        //JButton ajouterButton = createStyledButton("Ajouter", bleuClair, e -> ajouterCandidature());
        JButton modifierButton = createStyledButton("Modifier", bleuClair, e -> modifierCandidature());
        JButton supprimerButton = createStyledButton("Supprimer", bleuClair, e -> supprimerCandidature());
        JButton refreshButton = createStyledButton("Actualiser", bleuFonce, e -> loadCandidatures());

        //buttonPanel.add(ajouterButton);
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
        String offreTitle = (String) tableModel.getValueAt(selectedRow, 1);
        String statutActuel = (String) tableModel.getValueAt(selectedRow, 4);
        int noteActuelle = (int) tableModel.getValueAt(selectedRow, 5);

        // Création de la fenêtre de dialogue
        JDialog modifierDialog = new JDialog(this, "Modifier la candidature", true);
        modifierDialog.setSize(400, 300);
        modifierDialog.setLocationRelativeTo(this);
        modifierDialog.setLayout(new BorderLayout());

        // Panel principal avec padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Info candidature
        JLabel titleLabel = new JLabel("Modification de candidature");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        titleLabel.setForeground(bleuFonce);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel offreLabel = new JLabel("Offre: " + offreTitle);
        offreLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        offreLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Panel pour le statut
        JPanel statutPanel = new JPanel();
        statutPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        statutPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JLabel statutLabel = new JLabel("Statut: ");
        statutLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        String[] statutOptions = {"En attente", "En cours", "Accepté", "Refusé"};
        JComboBox<String> statutComboBox = new JComboBox<>(statutOptions);
        statutComboBox.setSelectedItem(statutActuel);
        statutComboBox.setPreferredSize(new Dimension(150, 30));

        statutPanel.add(statutLabel);
        statutPanel.add(statutComboBox);

        // Panel pour la note
        JPanel notePanel = new JPanel();
        notePanel.setLayout(new BoxLayout(notePanel, BoxLayout.Y_AXIS));
        notePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        JLabel noteLabel = new JLabel("Note: " + noteActuelle + "%");
        noteLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        noteLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JSlider noteSlider = new JSlider(0, 100, noteActuelle);
        noteSlider.setMajorTickSpacing(20);
        noteSlider.setMinorTickSpacing(5);
        noteSlider.setPaintTicks(true);
        noteSlider.setPaintLabels(true);
        noteSlider.setPreferredSize(new Dimension(350, 50));
        noteSlider.setAlignmentX(Component.LEFT_ALIGNMENT);

        noteSlider.addChangeListener(e -> {
            int valeur = noteSlider.getValue();
            noteLabel.setText("Note: " + valeur + "%");
        });

        notePanel.add(noteLabel);
        notePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        notePanel.add(noteSlider);

        // Panel pour les boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton saveButton = createStyledButton("Enregistrer", bleuClair, e -> {
            try {
                CandidatureDAOImpl dao = new CandidatureDAOImpl();
                Candidature candidature = new Candidature();
                candidature.setIdAnnonce(idAnnonce);
                candidature.setIdDemandeur(idDemandeur);
                candidature.setStatut((String) statutComboBox.getSelectedItem());
                candidature.setNote(noteSlider.getValue());

                if (dao.updateCandidature(candidature)) {
                    JOptionPane.showMessageDialog(modifierDialog,
                            "Candidature mise à jour avec succès",
                            "Succès",
                            JOptionPane.INFORMATION_MESSAGE);
                    modifierDialog.dispose();
                    loadCandidatures(); // Rafraîchir la table
                } else {
                    JOptionPane.showMessageDialog(modifierDialog,
                            "Erreur lors de la mise à jour de la candidature",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(modifierDialog,
                        "Erreur lors de la mise à jour: " + ex.getMessage(),
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton cancelButton = createStyledButton("Annuler", Color.GRAY, e -> modifierDialog.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        // Ajouter tous les composants au panel principal
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(offreLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(statutPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(notePanel);

        // Ajouter les panels au dialog
        modifierDialog.add(mainPanel, BorderLayout.CENTER);
        modifierDialog.add(buttonPanel, BorderLayout.SOUTH);

        modifierDialog.setVisible(true);
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
        int idDemandeur = (int) tableModel.getValueAt(selectedRow, 2);
        String offre = (String) tableModel.getValueAt(selectedRow, 1);

        int option = JOptionPane.showConfirmDialog(this,
                "Voulez-vous vraiment supprimer la candidature pour l'offre : " + offre + "?",
                "Confirmation de suppression",
                JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            try {
                CandidatureDAOImpl dao = new CandidatureDAOImpl();
                if (dao.deleteCandidature(idAnnonce,idDemandeur) ) {
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
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Erreur lors de la suppression: " + e.getMessage(),
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
} 