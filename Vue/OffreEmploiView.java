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
import java.util.List;

public class OffreEmploiView extends JFrame {
    private JTable annoncesTable;
    private DefaultTableModel tableModel;

    public OffreEmploiView() {
        setTitle("MatchaJob - Offres d'emploi");
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
        JLabel titre = new JLabel("Nos offres d'emploi", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 24));
        titre.setForeground(bleuFonce);
        contentPanel.add(titre, BorderLayout.NORTH);

        // Tableau des annonces
        initializeTableModel();
        configureTableAppearance(bleuFonce, bleuClair);

        // Boutons
        JPanel buttonPanel = createButtonPanel(bleuFonce, bleuClair);

        // Assemblage
        contentPanel.add(new JScrollPane(annoncesTable), BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Chargement des données
        loadAnnonces();

        return contentPanel;
    }

    private void initializeTableModel() {
        String[] columnNames = {"Titre", "Description", "Salaire", "Lieu", "Type de contrat", "Expérience requise", "Date début"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        annoncesTable = new JTable(tableModel);
    }

    private void configureTableAppearance(Color bleuFonce, Color bleuClair) {
        annoncesTable.setRowHeight(30);
        annoncesTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        annoncesTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        annoncesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        annoncesTable.setIntercellSpacing(new Dimension(0, 0));
        annoncesTable.setShowGrid(false);
        annoncesTable.setSelectionBackground(bleuClair);
        annoncesTable.setSelectionForeground(Color.WHITE);
    }

    private JPanel createButtonPanel(Color bleuFonce, Color bleuClair) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton postulerButton = createStyledButton("Postuler", bleuClair, e -> postulerAction());
        JButton refreshButton = createStyledButton("Actualiser", bleuFonce, e -> loadAnnonces());

        buttonPanel.add(postulerButton);
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
        verticalScrollBar.setForeground(new Color(45, 132, 255));
        verticalScrollBar.setUnitIncrement(16);
        verticalScrollBar.setPreferredSize(new Dimension(10, 0));
    }

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
            JOptionPane.showMessageDialog(this,
                    "Votre candidature a été envoyée avec succès!",
                    "Succès",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}