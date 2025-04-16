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

    public EntreprisePage(EmployeurDAOImpl clientDAO) {
        this.clientDAO = clientDAO;
        
        setTitle("MatchaJob - Entreprises");
        setSize(1200, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Couleurs principales
        Color bleuFonce = new Color(9, 18, 66);
        Color blanc = Color.WHITE;

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(bleuFonce);

        // Ajout des composants
        mainPanel.add(new HeaderComponent(this), BorderLayout.NORTH);
        mainPanel.add(createContentPanel(), BorderLayout.CENTER);
        mainPanel.add(new FooterComponent(), BorderLayout.SOUTH);

        // Configuration du défilement
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Style de la barre de défilement
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setBackground(Color.WHITE);
        verticalScrollBar.setForeground(new Color(45, 132, 255));
        verticalScrollBar.setUnitIncrement(16);
        verticalScrollBar.setPreferredSize(new Dimension(10, 0));

        add(scrollPane);
        setVisible(true);
    }

    private JPanel createContentPanel() {
        Color bleuFonce = new Color(9, 18, 66);
        Color blanc = Color.WHITE;

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(bleuFonce);
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Panel des filtres
        filterPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        filterPanel.setBackground(bleuFonce);
        filterPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(blanc),
            "Filtres de recherche",
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            new Font("SansSerif", Font.BOLD, 16),
            blanc
        ));

        // Ajout des filtres
        initializeFilters();

        // Panel des résultats
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBackground(bleuFonce);
        resultPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(blanc),
            "Résultats",
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            new Font("SansSerif", Font.BOLD, 16),
            blanc
        ));

        // Création du modèle de table
        String[] columnNames = {"ID", "Nom", "Secteur d'activité", "Adresse", "Téléphone", "Email", "Description"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Création de la table
        resultTable = new JTable(tableModel);
        resultTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        resultTable.setForeground(blanc);
        resultTable.setBackground(bleuFonce);
        resultTable.setGridColor(new Color(45, 132, 255));
        resultTable.setSelectionBackground(new Color(45, 132, 255));
        resultTable.setSelectionForeground(blanc);
        resultTable.setRowHeight(30);
        resultTable.setShowGrid(true);

        // Style des en-têtes
        JTableHeader header = resultTable.getTableHeader();
        header.setBackground(bleuFonce);
        header.setForeground(blanc);
        header.setFont(new Font("SansSerif", Font.BOLD, 14));

        // Configuration des colonnes
        TableColumnModel columnModel = resultTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);  // ID
        columnModel.getColumn(1).setPreferredWidth(150); // Nom
        columnModel.getColumn(2).setPreferredWidth(150); // Secteur d'activité
        columnModel.getColumn(3).setPreferredWidth(200); // Adresse
        columnModel.getColumn(4).setPreferredWidth(100); // Téléphone
        columnModel.getColumn(5).setPreferredWidth(150); // Email
        columnModel.getColumn(6).setPreferredWidth(300); // Description

        JScrollPane resultScrollPane = new JScrollPane(resultTable);
        resultScrollPane.setBorder(BorderFactory.createEmptyBorder());
        resultScrollPane.getViewport().setBackground(bleuFonce);

        // Panel des boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(bleuFonce);

        executeButton = new JButton("Rechercher");
        clearButton = new JButton("Effacer les filtres");

        // Style des boutons
        for (JButton button : new JButton[]{executeButton, clearButton}) {
            button.setBackground(new Color(45, 132, 255));
            button.setForeground(blanc);
            button.setFont(new Font("SansSerif", Font.BOLD, 14));
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.setPreferredSize(new Dimension(150, 40));
        }

        buttonPanel.add(executeButton);
        buttonPanel.add(clearButton);

        resultPanel.add(resultScrollPane, BorderLayout.CENTER);
        resultPanel.add(buttonPanel, BorderLayout.SOUTH);

        contentPanel.add(filterPanel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(resultPanel);

        setupEventHandlers();

        return contentPanel;
    }

    private void initializeFilters() {
        Color blanc = Color.WHITE;
        
        // Filtre secteur informatique
        JCheckBox secteurInfoCheckBox = new JCheckBox("Secteur Informatique");
        secteurInfoCheckBox.setForeground(blanc);
        secteurInfoCheckBox.setBackground(new Color(9, 18, 66));
        secteurInfoCheckBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        filterPanel.add(secteurInfoCheckBox);

        // Filtre secteur santé
        JCheckBox secteurSanteCheckBox = new JCheckBox("Secteur Santé");
        secteurSanteCheckBox.setForeground(blanc);
        secteurSanteCheckBox.setBackground(new Color(9, 18, 66));
        secteurSanteCheckBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        filterPanel.add(secteurSanteCheckBox);

        // Filtre description détaillée
        JCheckBox descriptionCheckBox = new JCheckBox("Description détaillée");
        descriptionCheckBox.setForeground(blanc);
        descriptionCheckBox.setBackground(new Color(9, 18, 66));
        descriptionCheckBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        filterPanel.add(descriptionCheckBox);

        // Filtre email valide
        JCheckBox emailCheckBox = new JCheckBox("Email valide");
        emailCheckBox.setForeground(blanc);
        emailCheckBox.setBackground(new Color(9, 18, 66));
        emailCheckBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        filterPanel.add(emailCheckBox);
    }

    private void setupEventHandlers() {
        executeButton.addActionListener(e -> {
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

        clearButton.addActionListener(e -> {
            // Réinitialiser tous les checkboxes
            for (Component comp : filterPanel.getComponents()) {
                if (comp instanceof JCheckBox) {
                    ((JCheckBox) comp).setSelected(false);
                }
            }
            tableModel.setRowCount(0);
        });
    }

    private String buildQuery() {
        StringBuilder query = new StringBuilder("SELECT * FROM societe");
        
        // Vérifier si au moins un filtre est utilisé
        boolean hasFilter = false;
        
        // Parcourir tous les checkboxes
        for (Component comp : filterPanel.getComponents()) {
            if (comp instanceof JCheckBox) {
                JCheckBox checkBox = (JCheckBox) comp;
                if (checkBox.isSelected()) {
                    if (!hasFilter) {
                        query.append(" WHERE");
                        hasFilter = true;
                    } else {
                        query.append(" AND");
                    }
                    
                    if (checkBox.getText().equals("Secteur Informatique")) {
                        query.append(" secteur_activite_societe LIKE '%informatique%'");
                    } else if (checkBox.getText().equals("Secteur Santé")) {
                        query.append(" secteur_activite_societe LIKE '%santé%' OR secteur_activite_societe LIKE '%sante%'");
                    } else if (checkBox.getText().equals("Description détaillée")) {
                        query.append(" LENGTH(description_societe) > 100");
                    } else if (checkBox.getText().equals("Email valide")) {
                        query.append(" email_societe LIKE '%@%.%'");
                    }
                }
            }
        }
        
        return query.toString();
    }

    private void displayResults(List<String> results) {
        // Vider la table
        tableModel.setRowCount(0);

        // Ignorer la première ligne (en-tête) et traiter les données
        for (int i = 1; i < results.size(); i++) {
            String row = results.get(i);
            // Diviser la ligne en colonnes en utilisant les espaces fixes
            String[] parts = new String[7];
            int startIndex = 0;
            for (int j = 0; j < 7; j++) {
                if (startIndex < row.length()) {
                    int endIndex = Math.min(startIndex + 20, row.length());
                    parts[j] = row.substring(startIndex, endIndex).trim();
                    startIndex = endIndex;
                } else {
                    parts[j] = "";
                }
            }
            
            // Ajouter la ligne au modèle de table
            tableModel.addRow(parts);
        }
    }
} 