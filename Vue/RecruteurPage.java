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

    private JPanel createContentPanel(Color bleuFonce, Color bleuClair) {
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titre = new JLabel("Recherche d'offres d'emploi", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 24));
        titre.setForeground(bleuFonce);
        contentPanel.add(titre, BorderLayout.NORTH);

        // Panel des filtres
        filterPanel = new JPanel(new GridLayout(0, 2, 10, 10));
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
        String[] columnNames = {"ID", "Titre", "Description", "Expérience", "Salaire", "Lieu", "Type de contrat"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Création de la table
        resultTable = new JTable(tableModel);
        configureTableAppearance(bleuFonce, bleuClair);

        JScrollPane resultScrollPane = new JScrollPane(resultTable);
        resultScrollPane.setBorder(BorderFactory.createEmptyBorder());
        resultScrollPane.getViewport().setBackground(Color.WHITE);

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
            departementField.setText("");
            salaireMinField.setText("");
            salaireMaxField.setText("");
            experienceComboBox.setSelectedIndex(0);
            tableModel.setRowCount(0);
        });

        buttonPanel.add(executeButton);
        buttonPanel.add(clearButton);

        resultPanel.add(resultScrollPane, BorderLayout.CENTER);
        resultPanel.add(buttonPanel, BorderLayout.SOUTH);

        JPanel centerPanel = new JPanel(new BorderLayout(0, 20));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(filterPanel, BorderLayout.NORTH);
        centerPanel.add(resultPanel, BorderLayout.CENTER);

        contentPanel.add(centerPanel, BorderLayout.CENTER);

        return contentPanel;
    }

    private void configureTableAppearance(Color bleuFonce, Color bleuClair) {
        resultTable.setRowHeight(30);
        resultTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        resultTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        resultTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        resultTable.setIntercellSpacing(new Dimension(0, 0));
        resultTable.setShowGrid(false);
        resultTable.setSelectionBackground(bleuClair);
        resultTable.setSelectionForeground(Color.WHITE);
        resultTable.setBackground(Color.WHITE);
        resultTable.setForeground(bleuFonce);
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
        verticalScrollBar.setForeground(new Color(45, 132, 255));
        verticalScrollBar.setUnitIncrement(16);
        verticalScrollBar.setPreferredSize(new Dimension(10, 0));
    }

    private void initializeFilters() {
        Color bleuFonce = new Color(9, 18, 66);
        
        // Département
        JLabel departementLabel = new JLabel("Département :");
        departementLabel.setForeground(bleuFonce);
        departementLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        departementField = new JTextField();
        departementField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        filterPanel.add(departementLabel);
        filterPanel.add(departementField);

        // Salaire minimum
        JLabel salaireMinLabel = new JLabel("Salaire minimum :");
        salaireMinLabel.setForeground(bleuFonce);
        salaireMinLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        salaireMinField = new JTextField();
        salaireMinField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        filterPanel.add(salaireMinLabel);
        filterPanel.add(salaireMinField);

        // Salaire maximum
        JLabel salaireMaxLabel = new JLabel("Salaire maximum :");
        salaireMaxLabel.setForeground(bleuFonce);
        salaireMaxLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        salaireMaxField = new JTextField();
        salaireMaxField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        filterPanel.add(salaireMaxLabel);
        filterPanel.add(salaireMaxField);

        // Expérience
        JLabel experienceLabel = new JLabel("Expérience requise :");
        experienceLabel.setForeground(bleuFonce);
        experienceLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        String[] experiences = {"Tous niveaux", "Débutant", "1-3 ans", "3-5 ans", "5-10 ans", "10+ ans"};
        experienceComboBox = new JComboBox<>(experiences);
        experienceComboBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        filterPanel.add(experienceLabel);
        filterPanel.add(experienceComboBox);
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
