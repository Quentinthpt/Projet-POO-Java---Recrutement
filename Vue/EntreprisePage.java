package Vue;

import DAO.EmployeurDAOImpl;
import Vue.Components.FooterComponent;
import Vue.Components.HeaderComponent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

public class EntreprisePage extends JFrame {
    private JPanel filterPanel;
    private JTable resultTable;
    private JButton executeButton;
    private JButton clearButton;
    private EmployeurDAOImpl clientDAO;
    private DefaultTableModel tableModel;

    // Nouveaux champs pour les filtres
    private JComboBox<String> secteurComboBox;
    private JTextField nomEntrepriseField;
    private JTextField villeField;
    private JTextField descriptionField;

    public EntreprisePage(EmployeurDAOImpl clientDAO) {
        this.clientDAO = clientDAO;

        setTitle("MatchaJob - Entreprises");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Color bleuFonce = new Color(9, 18, 66);
        Color bleuClair = new Color(45, 132, 255);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new HeaderComponent(this), BorderLayout.NORTH);
        mainPanel.add(createContentPanel(bleuFonce, bleuClair), BorderLayout.CENTER);
        mainPanel.add(new FooterComponent(), BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        configureScrollPane(scrollPane);

        add(scrollPane);
        setVisible(true);
    }

    private void configureScrollPane(JScrollPane scrollPane) {
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Style de la barre de défilement
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setBackground(Color.WHITE);
        verticalScrollBar.setForeground(new Color(45, 132, 255));
        verticalScrollBar.setUnitIncrement(16);
        verticalScrollBar.setPreferredSize(new Dimension(10, 0));
    }

    private JPanel createContentPanel(Color bleuFonce, Color bleuClair) {
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(Color.WHITE);

        // Titre
        JLabel titre = new JLabel("Recherche d'entreprises", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 24));
        titre.setForeground(bleuFonce);
        contentPanel.add(titre, BorderLayout.NORTH);

        // Panel des filtres
        filterPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(bleuFonce),
                "Filtres de recherche",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("SansSerif", Font.BOLD, 16),
                bleuFonce
        ));

        // Ajout des filtres
        initializeFilters();

        // Panel des résultats
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBackground(Color.WHITE);
        resultPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(bleuFonce),
                "Résultats",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("SansSerif", Font.BOLD, 16),
                bleuFonce
        ));

        // Création du modèle de table
        String[] columnNames = {"Nom", "Secteur d'activité", "Adresse", "Numéro de téléphone", "Email", "Description"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Création de la table
        resultTable = new JTable(tableModel);
        resultTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        resultTable.setForeground(bleuFonce);
        resultTable.setBackground(Color.WHITE);
        resultTable.setGridColor(bleuClair);
        resultTable.setSelectionBackground(bleuClair);
        resultTable.setSelectionForeground(Color.WHITE);
        resultTable.setRowHeight(30);
        resultTable.setShowGrid(false);
        resultTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        resultTable.getTableHeader().setBackground(bleuFonce);
        resultTable.getTableHeader().setForeground(Color.WHITE);

        // Configuration des colonnes
        TableColumnModel columnModel = resultTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(150); // Nom
        columnModel.getColumn(1).setPreferredWidth(150); // Secteur d'activité
        columnModel.getColumn(2).setPreferredWidth(200); // Adresse
        columnModel.getColumn(3).setPreferredWidth(100); // Numéro de téléphone
        columnModel.getColumn(4).setPreferredWidth(150); // Email
        columnModel.getColumn(5).setPreferredWidth(300); // Description

        JScrollPane resultScrollPane = new JScrollPane(resultTable);
        resultScrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Panel des boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);

        executeButton = createStyledButton("Rechercher", bleuClair, e -> {
            try {
                String query = buildQuery();
                List<String> results = clientDAO.executeQuery(query);
                displayResults(results);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,
                        "Erreur SQL: " + ex.getMessage(),
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        clearButton = createStyledButton("Effacer les filtres", bleuFonce, e -> {
            secteurComboBox.setSelectedIndex(0);
            nomEntrepriseField.setText("");
            villeField.setText("");
            descriptionField.setText("");
            tableModel.setRowCount(0);
        });

        buttonPanel.add(executeButton);
        buttonPanel.add(clearButton);

        resultPanel.add(resultScrollPane, BorderLayout.CENTER);
        resultPanel.add(buttonPanel, BorderLayout.SOUTH);

        contentPanel.add(filterPanel, BorderLayout.NORTH);
        contentPanel.add(resultPanel, BorderLayout.CENTER);

        return contentPanel;
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

    private void initializeFilters() {
        Color bleuFonce = new Color(9, 18, 66);

        // Secteur d'activité
        JLabel secteurLabel = new JLabel("Secteur d'activité :");
        secteurLabel.setForeground(bleuFonce);
        secteurLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        String[] secteurs = {"Tous les secteurs", "Informatique", "Santé", "Finance", "Industrie", "Commerce", "Services"};
        secteurComboBox = new JComboBox<>(secteurs);
        secteurComboBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JPanel secteurPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        secteurPanel.setBackground(Color.WHITE);
        secteurPanel.add(secteurLabel);
        secteurPanel.add(secteurComboBox);
        filterPanel.add(secteurPanel);

        // Nom de l'entreprise
        JLabel nomLabel = new JLabel("Nom de l'entreprise :");
        nomLabel.setForeground(bleuFonce);
        nomLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        nomEntrepriseField = new JTextField(20);
        nomEntrepriseField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JPanel nomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        nomPanel.setBackground(Color.WHITE);
        nomPanel.add(nomLabel);
        nomPanel.add(nomEntrepriseField);
        filterPanel.add(nomPanel);

        // Ville
        JLabel villeLabel = new JLabel("Adresse de l'entreprise :");
        villeLabel.setForeground(bleuFonce);
        villeLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        villeField = new JTextField(20);
        villeField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JPanel villePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        villePanel.setBackground(Color.WHITE);
        villePanel.add(villeLabel);
        villePanel.add(villeField);
        filterPanel.add(villePanel);

        // Description
        JLabel descriptionLabel = new JLabel("Description :");
        descriptionLabel.setForeground(bleuFonce);
        descriptionLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        descriptionField = new JTextField(20);
        descriptionField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JPanel descriptionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        descriptionPanel.setBackground(Color.WHITE);
        descriptionPanel.add(descriptionLabel);
        descriptionPanel.add(descriptionField);
        filterPanel.add(descriptionPanel);
    }

    private String buildQuery() {
        StringBuilder query = new StringBuilder("SELECT CONCAT_WS('|', nom_societe, secteur_activite_societe, adresse_societe, numero_telephone_societe, email_societe, description_societe) FROM societe");

        // Vérifier si au moins un filtre est utilisé
        boolean hasFilter = false;

        // Filtre secteur d'activité
        String secteur = (String) secteurComboBox.getSelectedItem();
        if (!secteur.equals("Tous les secteurs")) {
            if (!hasFilter) {
                query.append(" WHERE");
                hasFilter = true;
            } else {
                query.append(" AND");
            }
            query.append(" secteur_activite_societe LIKE '%").append(secteur).append("%'");
        }

        // Filtre nom de l'entreprise
        String nomEntreprise = nomEntrepriseField.getText().trim();
        if (!nomEntreprise.isEmpty()) {
            if (!hasFilter) {
                query.append(" WHERE");
                hasFilter = true;
            } else {
                query.append(" AND");
            }
            query.append(" nom_societe LIKE '%").append(nomEntreprise).append("%'");
        }

        // Filtre ville
        String ville = villeField.getText().trim();
        if (!ville.isEmpty()) {
            if (!hasFilter) {
                query.append(" WHERE");
                hasFilter = true;
            } else {
                query.append(" AND");
            }
            query.append(" adresse_societe LIKE '%").append(ville).append("%'");
        }

        // Filtre description
        String description = descriptionField.getText().trim();
        if (!description.isEmpty()) {
            if (!hasFilter) {
                query.append(" WHERE");
                hasFilter = true;
            } else {
                query.append(" AND");
            }
            query.append(" description_societe LIKE '%").append(description).append("%'");
        }

        return query.toString();
    }

    private void displayResults(List<String> results) {
        // Vider la table
        tableModel.setRowCount(0);

        // Ignorer la première ligne (en-tête) et traiter les données
        for (int i = 1; i < results.size(); i++) {
            String row = results.get(i);
            // Utiliser un séparateur plus fiable pour découper les données
            String[] parts = row.split("\\|", -1); // -1 pour garder les chaînes vides
            
            // S'assurer que nous avons le bon nombre de colonnes
            if (parts.length == 6) {
                tableModel.addRow(parts);
            }
        }
    }
} 