package Vue;

import Modele.SessionUtilisateur;
import javax.swing.*;
import java.awt.*;

public class CandidatureView extends JFrame {
    public CandidatureView() {
        setTitle("Mes Candidatures - MatchaJob");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Changé pour ne pas quitter l'application
        setLocationRelativeTo(null);

        Color bleuFonce = new Color(9, 18, 66);
        Color bleuClair = new Color(45, 132, 255);
        Color blanc = Color.WHITE;

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(bleuFonce);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        JLabel header = new JLabel("Mes Candidatures", SwingConstants.CENTER);
        header.setForeground(blanc);
        header.setFont(new Font("SansSerif", Font.BOLD, 24));
        header.setOpaque(true);
        header.setBackground(bleuFonce);
        header.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
        //header.setPreferredSize(new Dimension(1000, 60));


        // Tableau des candidatures (à remplacer par des données réelles)
        String[][] data = {
                {"1", "Développeur Web", "En cours", "01/04/2025"},
                {"2", "Chargé de projet", "Accepté", "03/04/2025"}
        };
        String[] cols = {"ID", "Poste", "Statut", "Date"};

        JTable table = new JTable(data, cols) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Style du tableau
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        table.setSelectionBackground(bleuClair);
        table.setSelectionForeground(blanc);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton detailsBtn = new JButton("Voir détails");
        detailsBtn.setBackground(bleuClair);
        detailsBtn.setForeground(blanc);
        detailsBtn.addActionListener(e -> voirDetailsCandidature());

        JButton retourBtn = new JButton("Retour");
        retourBtn.setBackground(bleuFonce);
        retourBtn.setForeground(blanc);
        retourBtn.addActionListener(e -> {
            dispose();
            new MainPage();
        });

        buttonPanel.add(detailsBtn);
        buttonPanel.add(retourBtn);

        // Assemblage
        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private void voirDetailsCandidature() {
        // À implémenter : affichage des détails de la candidature sélectionnée
        JOptionPane.showMessageDialog(this,
                "Fonctionnalité de détails à implémenter",
                "Information",
                JOptionPane.INFORMATION_MESSAGE);
    }
}