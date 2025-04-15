package Vue;

import Modele.SessionUtilisateur;

import javax.swing.*;
import java.awt.*;

public class ProfilPage extends JFrame {
    public ProfilPage() {
        setTitle("Mon Profil - MatchaJob");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Changé pour ne pas quitter l'app
        setLocationRelativeTo(null);

        Color bleuFonce = new Color(9, 18, 66);
        Color blanc = Color.WHITE;

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(bleuFonce);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Récupération des infos de la session
        SessionUtilisateur session = SessionUtilisateur.getInstance();

        // Header
        JLabel header = new JLabel("Mon Profil", SwingConstants.CENTER);
        header.setForeground(blanc);
        header.setFont(new Font("SansSerif", Font.BOLD, 24));
        header.setOpaque(true);
        header.setBackground(bleuFonce);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Tableau des informations
        String[][] data = {
                {"Nom", session.getNom()},
                {"Prénom", session.getPrenom()},
                {"Email", session.getEmail()},
                {"Adresse", session.getAdresse()},
                {"Âge", String.valueOf(session.getAge())},
                {"Expérience", session.getExperience()},
                {"CV", session.getCv()}
        };
        String[] cols = {"Information", "Valeur"};

        JTable table = new JTable(data, cols) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tableau non éditable
            }
        };

        // Style du tableau
        table.setFont(new Font("SansSerif", Font.PLAIN, 16));
        table.setRowHeight(35);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 16));
        table.setGridColor(new Color(200, 200, 200));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton modifierBtn = new JButton("Modifier mon profil");
        modifierBtn.setBackground(new Color(45, 132, 255));
        modifierBtn.setForeground(blanc);
        modifierBtn.addActionListener(e -> ouvrirModificationProfil());

        JButton retourBtn = new JButton("Retour");
        retourBtn.setBackground(bleuFonce);
        retourBtn.setForeground(blanc);
        retourBtn.addActionListener(e -> {
            dispose();
            new MainPage(); // Plus besoin de passer le demandeur
        });

        buttonPanel.add(modifierBtn);
        buttonPanel.add(retourBtn);

        // Assemblage
        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private void ouvrirModificationProfil() {
        // À implémenter : ouverture de la vue de modification
        JOptionPane.showMessageDialog(this,
                "Fonctionnalité de modification à implémenter",
                "Information",
                JOptionPane.INFORMATION_MESSAGE);
    }
}