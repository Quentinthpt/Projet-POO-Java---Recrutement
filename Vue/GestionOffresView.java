package Vue;

import DAO.OffreEmploiDAOImpl;
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
import java.util.Date;
import java.util.List;

public class GestionOffresView extends JFrame {
    private JTable offresTable;
    private DefaultTableModel tableModel;
    private Color bleuFonce = new Color(9, 18, 66);
    private Color bleuClair = new Color(45, 132, 255);

    public GestionOffresView() {
        setTitle("MatchaJob - Gestion des offres d'emploi");
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

        JLabel titre = new JLabel("Gestion des offres d'emploi", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 24));
        titre.setForeground(bleuFonce);
        contentPanel.add(titre, BorderLayout.NORTH);

        initializeTableModel();
        configureTableAppearance();

        JPanel buttonPanel = createButtonPanel();

        contentPanel.add(new JScrollPane(offresTable), BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        loadOffres();

        return contentPanel;
    }

    private void initializeTableModel() {
        String[] columnNames = {
                "ID", "Titre", "Description", "Salaire", "Lieu",
                "Type de contrat", "Expérience requise", "Date début"
        };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0; // L'ID n'est pas modifiable
            }
        };
        offresTable = new JTable(tableModel);
    }

    private void configureTableAppearance() {
        offresTable.setRowHeight(30);
        offresTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        offresTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        offresTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        offresTable.setIntercellSpacing(new Dimension(0, 0));
        offresTable.setShowGrid(false);
        offresTable.setSelectionBackground(bleuClair);
        offresTable.setSelectionForeground(Color.WHITE);
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton ajouterButton = createStyledButton("Ajouter", bleuClair, e -> ajouterOffre());
        JButton modifierButton = createStyledButton("Modifier", bleuClair, e -> modifierOffre());
        JButton supprimerButton = createStyledButton("Supprimer", bleuClair, e -> supprimerOffre());
        JButton refreshButton = createStyledButton("Actualiser", bleuFonce, e -> loadOffres());

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

    private void loadOffres() {
        try {
            OffreEmploiDAOImpl dao = new OffreEmploiDAOImpl();
            List<Annonce> offres = dao.getAllAnnonces();

            tableModel.setRowCount(0);

            for (Annonce offre : offres) {
                Object[] rowData = {
                        offre.getId(),
                        offre.getTitre(),
                        offre.getDescription(),
                        offre.getSalaire() + " €",
                        offre.getLieuTravail(),
                        offre.getTypeContrat(),
                        offre.getExperienceRequise(),
                        offre.getDateDebut()
                };
                tableModel.addRow(rowData);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Erreur lors du chargement des offres: " + e.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void ajouterOffre() {
        JPanel panel = new JPanel(new GridLayout(8, 2, 5, 5));
        
        JTextField titreField = new JTextField();
        JTextArea descriptionArea = new JTextArea(3, 20);
        JTextField salaireField = new JTextField();
        JTextField lieuField = new JTextField();
        
        // Création du JComboBox pour le type de contrat
        String[] typesContrat = {"CDD", "CDI", "Stage", "Alternance"};
        JComboBox<String> typeContratCombo = new JComboBox<>(typesContrat);
        
        // Création du JComboBox pour l'expérience requise
        String[] experiences = {"0-2 ans", "2-8 ans", "+8 ans"};
        JComboBox<String> experienceCombo = new JComboBox<>(experiences);
        
        // Création du Spinner pour la date
        SpinnerDateModel dateModel = new SpinnerDateModel();
        JSpinner dateSpinner = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(dateEditor);

        panel.add(new JLabel("Titre:"));
        panel.add(titreField);
        panel.add(new JLabel("Description:"));
        panel.add(new JScrollPane(descriptionArea));
        panel.add(new JLabel("Salaire (€):"));
        panel.add(salaireField);
        panel.add(new JLabel("Lieu:"));
        panel.add(lieuField);
        panel.add(new JLabel("Type de contrat:"));
        panel.add(typeContratCombo);
        panel.add(new JLabel("Expérience requise:"));
        panel.add(experienceCombo);
        panel.add(new JLabel("Date début:"));
        panel.add(dateSpinner);

        int result = JOptionPane.showConfirmDialog(this, panel, "Ajouter une nouvelle offre",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                // Validation des champs obligatoires
                if (titreField.getText().trim().isEmpty() || 
                    descriptionArea.getText().trim().isEmpty() || 
                    salaireField.getText().trim().isEmpty() || 
                    lieuField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Veuillez remplir tous les champs obligatoires",
                            "Champs manquants",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Annonce nouvelleOffre = new Annonce();
                nouvelleOffre.setTitre(titreField.getText().trim());
                nouvelleOffre.setDescription(descriptionArea.getText().trim());
                nouvelleOffre.setSalaire(Integer.parseInt(salaireField.getText().trim()));
                nouvelleOffre.setLieuTravail(lieuField.getText().trim());
                nouvelleOffre.setTypeContrat((String) typeContratCombo.getSelectedItem());
                nouvelleOffre.setExperienceRequise((String) experienceCombo.getSelectedItem());
                nouvelleOffre.setDateDebut(new java.sql.Date(((Date) dateSpinner.getValue()).getTime()));

                OffreEmploiDAOImpl dao = new OffreEmploiDAOImpl();
                dao.addAnnonce(nouvelleOffre);
                loadOffres();

                JOptionPane.showMessageDialog(this,
                        "Offre ajoutée avec succès",
                        "Succès",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "Le salaire doit être un nombre valide",
                        "Erreur de format",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Erreur lors de l'ajout: " + e.getMessage(),
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void modifierOffre() {
        int selectedRow = offresTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez sélectionner une offre à modifier",
                    "Aucune sélection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);

        // Récupérer d'abord l'annonce complète depuis la base de données
        // pour conserver toutes les valeurs, y compris id_societe
        try {
            OffreEmploiDAOImpl dao = new OffreEmploiDAOImpl();
            Annonce offreExistante = dao.getAnnonceById(id);

            if (offreExistante == null) {
                JOptionPane.showMessageDialog(this,
                        "Impossible de trouver l'offre à modifier",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Préparer le formulaire de modification avec les valeurs existantes
            String titre = (String) tableModel.getValueAt(selectedRow, 1);
            String description = (String) tableModel.getValueAt(selectedRow, 2);
            String salaire = ((String) tableModel.getValueAt(selectedRow, 3)).replace(" €", "");
            String lieu = (String) tableModel.getValueAt(selectedRow, 4);
            String typeContrat = (String) tableModel.getValueAt(selectedRow, 5);
            String experience = (String) tableModel.getValueAt(selectedRow, 6);
            String dateDebut = tableModel.getValueAt(selectedRow, 7).toString();

            JPanel panel = new JPanel(new GridLayout(7, 2, 5, 5));

            JTextField titreField = new JTextField(titre);
            JTextArea descriptionArea = new JTextArea(description, 3, 20);
            JTextField salaireField = new JTextField(salaire);
            JTextField lieuField = new JTextField(lieu);
            JTextField typeContratField = new JTextField(typeContrat);
            JTextField experienceField = new JTextField(experience);
            JTextField dateDebutField = new JTextField(dateDebut);

            panel.add(new JLabel("Titre:"));
            panel.add(titreField);
            panel.add(new JLabel("Description:"));
            panel.add(new JScrollPane(descriptionArea));
            panel.add(new JLabel("Salaire:"));
            panel.add(salaireField);
            panel.add(new JLabel("Lieu:"));
            panel.add(lieuField);
            panel.add(new JLabel("Type de contrat:"));
            panel.add(typeContratField);
            panel.add(new JLabel("Expérience requise:"));
            panel.add(experienceField);
            panel.add(new JLabel("Date début (YYYY-MM-DD):"));
            panel.add(dateDebutField);

            int result = JOptionPane.showConfirmDialog(this, panel, "Modifier l'offre",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                try {
                    // Mettre à jour uniquement les champs modifiés tout en conservant id_societe
                    offreExistante.setTitre(titreField.getText());
                    offreExistante.setDescription(descriptionArea.getText());
                    offreExistante.setSalaire(Integer.parseInt(salaireField.getText()));
                    offreExistante.setLieuTravail(lieuField.getText());
                    offreExistante.setTypeContrat(typeContratField.getText());
                    offreExistante.setExperienceRequise(experienceField.getText());
                    offreExistante.setDateDebut(java.sql.Date.valueOf(dateDebutField.getText()));
                    // id_societe reste inchangé car on utilise l'objet existant

                    dao.updateAnnonce(offreExistante);
                    loadOffres();

                    JOptionPane.showMessageDialog(this,
                            "Offre modifiée avec succès",
                            "Succès",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this,
                            "Erreur lors de la modification: " + e.getMessage(),
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erreur lors de la récupération de l'offre: " + e.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void supprimerOffre() {
        int selectedRow = offresTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez sélectionner une offre à supprimer",
                    "Aucune sélection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String titre = (String) tableModel.getValueAt(selectedRow, 1);

        int option = JOptionPane.showConfirmDialog(this,
                "Voulez-vous vraiment supprimer l'offre : " + titre + "?",
                "Confirmation de suppression",
                JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            try {
                OffreEmploiDAOImpl dao = new OffreEmploiDAOImpl();
                dao.deleteAnnonce(id);
                loadOffres();

                JOptionPane.showMessageDialog(this,
                        "Offre supprimée avec succès",
                        "Succès",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this,
                        "Erreur lors de la suppression: " + e.getMessage(),
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
} 