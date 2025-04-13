package Vue;

import Modele.DemandeurEmploi;

import javax.swing.*;
import java.awt.*;

public class ProfilPage extends JFrame {
    public ProfilPage(DemandeurEmploi demandeurEmploi) {
        setTitle("Mon Profil - MatchaJob");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Color bleuFonce = new Color(9, 18, 66);
        Color blanc = Color.WHITE;

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(bleuFonce);

        // Header simple
        JLabel header = new JLabel("Mon Profil", SwingConstants.CENTER);
        header.setForeground(blanc);
        header.setFont(new Font("SansSerif", Font.BOLD, 24));
        header.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));

        // Tableau infos
        String[][] data = {
                {"Nom", demandeurEmploi.getNom()},
                {"Prénom", demandeurEmploi.getPrenom()},
                {"Email", demandeurEmploi.getEmail()},
                {"Adresse", demandeurEmploi.getAdresse()},
                {"Âge", String.valueOf(demandeurEmploi.getAge())},
                {"Expérience", demandeurEmploi.getExperience()},
                {"CV", demandeurEmploi.getCv()}
        };
        String[] cols = {"Champ", "Valeur"};

        JTable table = new JTable(data, cols);
        table.setFont(new Font("SansSerif", Font.PLAIN, 16));
        table.setRowHeight(30);
        table.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }
}

