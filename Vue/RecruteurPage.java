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

public class RecruteurPage extends JFrame {
    private JPanel filterPanel;
    private JTable resultTable;
    private JButton executeButton;
    private JButton clearButton;
    private EmployeurDAOImpl clientDAO;
    
    // Nouveaux champs pour les filtres
    private JTextField departementField;
    private JTextField salaireMinField;
    private JTextField salaireMaxField;
    private JComboBox<String> experienceComboBox;

    // Modèle de table pour les résultats
    private DefaultTableModel tableModel;

    public RecruteurPage(EmployeurDAOImpl clientDAO) {
        this.clientDAO = clientDAO;
        
        setTitle("MatchaJob - Recruteurs");
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

        // Département
        JLabel departementLabel = new JLabel("Département :");
        departementLabel.setForeground(blanc);
        departementLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        departementField = new JTextField();
        departementField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        departementField.setBackground(blanc);
        filterPanel.add(departementLabel);
        filterPanel.add(departementField);

        // Salaire minimum
        JLabel salaireMinLabel = new JLabel("Salaire minimum :");
        salaireMinLabel.setForeground(blanc);
        salaireMinLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        salaireMinField = new JTextField();
        salaireMinField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        salaireMinField.setBackground(blanc);
        filterPanel.add(salaireMinLabel);
        filterPanel.add(salaireMinField);

        // Salaire maximum
        JLabel salaireMaxLabel = new JLabel("Salaire maximum :");
        salaireMaxLabel.setForeground(blanc);
        salaireMaxLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        salaireMaxField = new JTextField();
        salaireMaxField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        salaireMaxField.setBackground(blanc);
        filterPanel.add(salaireMaxLabel);
        filterPanel.add(salaireMaxField);

        // Expérience
        JLabel experienceLabel = new JLabel("Expérience requise :");
        experienceLabel.setForeground(blanc);
        experienceLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        String[] experiences = {"Tous niveaux", "Débutant", "1-3 ans", "3-5 ans", "5-10 ans", "10+ ans"};
        experienceComboBox = new JComboBox<>(experiences);
        experienceComboBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        experienceComboBox.setBackground(blanc);
        filterPanel.add(experienceLabel);
        filterPanel.add(experienceComboBox);

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
        String[] columnNames = {"ID", "Titre", "Description", "Expérience", "Salaire", "Lieu", "Type de contrat"};
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
        columnModel.getColumn(1).setPreferredWidth(150); // Titre
        columnModel.getColumn(2).setPreferredWidth(300); // Description
        columnModel.getColumn(3).setPreferredWidth(100); // Expérience
        columnModel.getColumn(4).setPreferredWidth(100); // Salaire
        columnModel.getColumn(5).setPreferredWidth(100); // Lieu
        columnModel.getColumn(6).setPreferredWidth(150); // Type de contrat

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
            departementField.setText("");
            salaireMinField.setText("");
            salaireMaxField.setText("");
            experienceComboBox.setSelectedIndex(0);
            tableModel.setRowCount(0);
        });
    }

    private String buildQuery() {
        StringBuilder query = new StringBuilder("SELECT * FROM annonce");
        
        // Vérifier si au moins un filtre est utilisé
        boolean hasFilter = false;
        
        // Filtre département
        String departement = departementField.getText().trim();
        if (!departement.isEmpty()) {
            if (!hasFilter) {
                query.append(" WHERE");
                hasFilter = true;
            } else {
                query.append(" AND");
            }
            query.append(" lieu_travail_annonce LIKE '%").append(departement).append("%'");
        }
        
        // Filtre salaire minimum
        String salaireMin = salaireMinField.getText().trim();
        if (!salaireMin.isEmpty()) {
            if (!hasFilter) {
                query.append(" WHERE");
                hasFilter = true;
            } else {
                query.append(" AND");
            }
            query.append(" salaire_annonce >= ").append(salaireMin);
        }
        
        // Filtre salaire maximum
        String salaireMax = salaireMaxField.getText().trim();
        if (!salaireMax.isEmpty()) {
            if (!hasFilter) {
                query.append(" WHERE");
                hasFilter = true;
            } else {
                query.append(" AND");
            }
            query.append(" salaire_annonce <= ").append(salaireMax);
        }
        
        // Filtre expérience
        String experience = (String) experienceComboBox.getSelectedItem();
        if (!experience.equals("Tous niveaux")) {
            if (!hasFilter) {
                query.append(" WHERE");
                hasFilter = true;
            } else {
                query.append(" AND");
            }
            switch (experience) {
                case "Débutant":
                    query.append(" experience_requise_annonce LIKE '%débutant%'");
                    break;
                case "1-3 ans":
                    query.append(" (experience_requise_annonce LIKE '%1%' OR experience_requise_annonce LIKE '%2%' OR experience_requise_annonce LIKE '%3%')");
                    break;
                case "3-5 ans":
                    query.append(" (experience_requise_annonce LIKE '%3%' OR experience_requise_annonce LIKE '%4%' OR experience_requise_annonce LIKE '%5%')");
                    break;
                case "5-10 ans":
                    query.append(" (experience_requise_annonce LIKE '%5%' OR experience_requise_annonce LIKE '%6%' OR experience_requise_annonce LIKE '%7%' OR experience_requise_annonce LIKE '%8%' OR experience_requise_annonce LIKE '%9%' OR experience_requise_annonce LIKE '%10%')");
                    break;
                case "10+ ans":
                    query.append(" (experience_requise_annonce LIKE '%10%' OR experience_requise_annonce LIKE '%plus%')");
                    break;
            }
        }
        
        return query.toString();
    }

    private void displayResults(List<String> results) {
        // Vider la table
        tableModel.setRowCount(0);

        // Ajouter les résultats
        for (String row : results) {
            String[] parts = row.split("\t");
            if (parts.length >= 7) {
                tableModel.addRow(new Object[]{
                    parts[0], // ID
                    parts[1], // Titre
                    parts[2], // Description
                    parts[3], // Expérience
                    parts[4], // Salaire
                    parts[5], // Lieu
                    parts[6]  // Type de contrat
                });
            }
        }
    }
}
