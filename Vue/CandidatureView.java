package Vue;

import DAO.CandidatureDAOImpl;
import Modele.Candidature;
import Modele.SessionUtilisateur;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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

        SessionUtilisateur session = SessionUtilisateur.getInstance();
        CandidatureDAOImpl candidatureDAO = new CandidatureDAOImpl();

        List<String[]> lignes = candidatureDAO.getInfosAnnoncesCandidature(session.getId());
        String[][] data = lignes.toArray(new String[0][]);
        String[] colonne = {"Titre", "Description", "Salaire", "Lieu",
                "Type de contrat", "Expérience requise", "Date début"};
/*
        for (int i = 0; i < candidatures.size(); i++) {
            Candidature candidature = candidatures.get(i);
            data[i][0] = String.valueOf(candidature.getIdAnnonce());
            data[i][1] = String.valueOf(candidature.getIdDemandeur());
            data[i][2] = candidature.getDateCandidature().toString();
            data[i][3] = candidature.getStatut();
            data[i][4] = String.valueOf(candidature.getNote());
            data[i][5] = candidature.getDocuments();
        }


        //String[] cols = {"ID Annonce" , "ID Demandeur", "Date Candidature", "Statut", "Note", "Documents"};
*/
        JTable table = new JTable(data, colonne){
            @Override
            public boolean isCellEditable(int row, int colonne) {
                return false;
            }
        };

/*
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
*/
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
        detailsBtn.addActionListener(e -> voirDetailsCandidature(table));

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

    private void voirDetailsCandidature(JTable table) {
        // À implémenter : affichage des détails de la candidature sélectionnée
        /*
        JOptionPane.showMessageDialog(this,
                "Fonctionnalité de détails à implémenter",
                "Information",
                JOptionPane.INFORMATION_MESSAGE);*/

        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez sélectionner une candidature",
                    "Aucune sélection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String Titre = table.getValueAt(selectedRow, 0).toString();
        String Description = table.getValueAt(selectedRow, 1).toString();
        String Salaire = table.getValueAt(selectedRow, 2).toString();
        String Lieu = table.getValueAt(selectedRow, 3).toString();
        String Type_de_Contrat = table.getValueAt(selectedRow, 4).toString();
        String Experience = table.getValueAt(selectedRow, 5).toString();
        String Date = table.getValueAt(selectedRow, 6).toString();

        String message = String.format(
                "Détails de la candidature:\n\n" +
                        "Titre: %s\n" +
                        "Description: %s\n" +
                        "Salaire: %s\n" +
                        "Lieu: %s\n" +
                        "Type de Contrat: %s\n" +
                        "Expérience requise: %s\n"+
                        "Date: %s",
                Titre, Description, Salaire, Lieu, Type_de_Contrat, Experience, Date);

        JOptionPane.showMessageDialog(this,
                message,
                "Détails de la candidature",
                JOptionPane.INFORMATION_MESSAGE);
    }
}