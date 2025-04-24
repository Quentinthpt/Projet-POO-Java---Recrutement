package Vue;

import DAO.CandidatureDAOImpl;
import Modele.SessionUtilisateur;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CandidatureView extends JFrame {
    private JTable table;
    private CandidatureDAOImpl candidatureDAO = new CandidatureDAOImpl();
    private String[] colonne = {"Titre", "Description", "Salaire", "Lieu",
            "Type de contrat", "Expérience requise", "Date début", "Statut"};
    private JButton supprimerBtn;


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

/*
        SessionUtilisateur session = SessionUtilisateur.getInstance();
        CandidatureDAOImpl candidatureDAO = new CandidatureDAOImpl();

        List<String[]> lignes = candidatureDAO.getInfosAnnoncesCandidature(session.getId());
        String[][] data = lignes.toArray(new String[0][]);
        String[] colonne = {"Titre", "Description", "Salaire", "Lieu",
                "Type de contrat", "Expérience requise", "Date début", "Statut"};

        JTable table = new JTable(data, colonne){
            @Override
            public boolean isCellEditable(int row, int colonne) {
                return false;
            }
        };

 */
        table = new JTable();
        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        loadTableData();

        // Style du tableau
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        table.setSelectionBackground(bleuClair);
        table.setSelectionForeground(blanc);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        table.getSelectionModel().addListSelectionListener(e -> {
            boolean isRowSelected = table.getSelectedRow() != -1;
            supprimerBtn.setEnabled(isRowSelected);
        });

        // Boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        supprimerBtn = new JButton("Supprimer");
        supprimerBtn.setEnabled(false);
        supprimerBtn.setBackground(bleuClair);
        supprimerBtn.setForeground(blanc);
        supprimerBtn.addActionListener(e -> supprimerCandidature());

        JButton retourBtn = new JButton("Retour");
        retourBtn.setBackground(bleuFonce);
        retourBtn.setForeground(blanc);
        retourBtn.addActionListener(e -> {
            dispose();
            new MainPage();
        });

        buttonPanel.add(supprimerBtn);
        buttonPanel.add(retourBtn);

        // Assemblage
        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private void loadTableData() {
        List<String[]> lignes = candidatureDAO.getInfosAnnoncesCandidature(SessionUtilisateur.getInstance().getId());
        String[][] data = lignes.toArray(new String[0][]);

        table.setModel(new javax.swing.table.DefaultTableModel(data, colonne) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }

    private void supprimerCandidature() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez sélectionner une candidature!",
                    "Aucune sélection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }


        int confirmation = JOptionPane.showConfirmDialog(this,
                "Voulez-vous vraiement supprimer cette candidature ?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            try {
                String titre = table.getValueAt(selectedRow, 0).toString();
                int idDemandeur = SessionUtilisateur.getInstance().getId();
                int idAnnonce = candidatureDAO.getIdAnnonceFromTitre(titre);
                boolean success = candidatureDAO.deleteCandidature(idAnnonce, idDemandeur);
                if (success){
                    JOptionPane.showMessageDialog(this, "Candidature supprimée avec succès !",
                            "Succès",
                            JOptionPane.INFORMATION_MESSAGE);
                    loadTableData();
                }
                else {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la suppression.",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }

            }catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erreur technique : " + ex.getMessage());
            }
        }
    }


}