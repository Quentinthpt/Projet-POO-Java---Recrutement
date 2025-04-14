package Vue;

import Modele.DemandeurEmploi;

import javax.swing.*;
import java.awt.*;


public class CandidatureView extends JFrame{
    public CandidatureView(DemandeurEmploi demandeurEmploi){
        setTitle("Mes Candidatures - MatchaJob");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Color bleuFonce = new Color(9, 18, 66);
        Color blanc = Color.WHITE;

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(bleuFonce);

        JLabel header = new JLabel("Mes Candidatures", SwingConstants.CENTER);
        header.setForeground(blanc);
        header.setFont(new Font("SansSerif", Font.BOLD, 24));
        header.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));


        // Tableau temporaire
        String[][] data = {
                {"1", "Développeur Web", "En cours", "01/04/2025"},
                {"2", "Chargé de projet", "Accepté", "03/04/2025"}
        };
        String[] cols = {"ID", "Poste", "Statut", "Date"};

        JTable table = new JTable(data, cols);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setRowHeight(28);
        table.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(table);

        JPanel footer = new JPanel();
        JButton retour = new JButton("Retour");
        retour.addActionListener(e -> {
           dispose();
           new DemandeurEmploiView(demandeurEmploi);
        });
        footer.add(retour);

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(footer, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }
}
