package Vue;

import DAO.OffreEmploiDAOImpl;
import DAO.CandidatureDAOImpl;
import DAO.CandidatureDAOImpl;

import Modele.Annonce;
import Modele.Candidature;
import Vue.Components.FooterComponent;
import Vue.Components.HeaderComponent;
import Modele.SessionUtilisateur;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
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

        JLabel titre = new JLabel("Nos offres d'emploi", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 24));
        titre.setForeground(bleuFonce);
        contentPanel.add(titre, BorderLayout.NORTH);

        initializeTableModel();
        configureTableAppearance(bleuFonce, bleuClair);

        JPanel buttonPanel = createButtonPanel(bleuFonce, bleuClair);

        contentPanel.add(new JScrollPane(annoncesTable), BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        loadAnnonces();

        return contentPanel;
    }

    private void initializeTableModel() {
        String[] columnNames = {
                "Titre", "Description", "Salaire", "Lieu",
                "Type de contrat", "Expérience requise", "Date début"
        };
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

            tableModel.setRowCount(0);

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
        return description.length() > 50 ? description.substring(0, 47) + "..." : description;
    }

    private void postulerAction() {
        System.out.println(">>> Début de la méthode postulerAction()");

        // Vérification de la connexion de l'utilisateur (ici on suppose un attribut "userId" dans la session)
        Integer userId = 0;
        if(SessionUtilisateur.getInstance().getEmail()!=null) {
            userId = SessionUtilisateur.getInstance().getId();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Vous devez être connecté pour postuler. Redirection vers la page de connexion.",
                    "Non connecté",
                    JOptionPane.WARNING_MESSAGE);
            redirectToLoginPage(); // Méthode fictive qui redirige l'utilisateur vers la page de connexion
            System.out.println("Utilisateur non connecté, redirection vers la page de connexion.");
            return;
        }
        System.out.println("Utilisateur connecté avec l'ID : " + userId);

        int selectedRow = annoncesTable.getSelectedRow();
        System.out.println("Ligne sélectionnée dans le tableau : " + selectedRow);

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez sélectionner une offre à laquelle postuler",
                    "Aucune sélection",
                    JOptionPane.WARNING_MESSAGE);
            System.out.println("Aucune offre sélectionnée. Fin de méthode.");
            return;
        }

        String titreOffre = (String) tableModel.getValueAt(selectedRow, 0);
        System.out.println("Titre de l'offre sélectionnée : " + titreOffre);

        int option = JOptionPane.showConfirmDialog(this,
                "Voulez-vous postuler à l'offre : " + titreOffre + "?",
                "Confirmation de postulation",
                JOptionPane.YES_NO_OPTION);

        System.out.println("Option de confirmation choisie : " + option);

        if (option == JOptionPane.YES_OPTION) {
            try {
                System.out.println("Création de l'objet DAO pour les offres...");
                OffreEmploiDAOImpl offreDAO = new OffreEmploiDAOImpl();
                System.out.println("Recherche de l'annonce par titre...");

                Annonce annonce = offreDAO.getAnnonceByTitre(titreOffre);
                System.out.println("Annonce récupérée : " + (annonce != null ? annonce : "null"));

                if (annonce == null) {
                    JOptionPane.showMessageDialog(this,
                            "Annonce introuvable.",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                    System.out.println("Annonce introuvable. Fin de méthode.");
                    return;
                }

                String document = "lettre_motivation_" + userId + ".pdf"; // Utilisation de l'ID du demandeur connecté

                System.out.println("Création de la candidature...");
                Candidature candidature = new Candidature();
                candidature.setIdAnnonce(annonce.getId());
                candidature.setIdDemandeur(userId);
                candidature.setDateCandidature(new java.util.Date());
                candidature.setStatut("En attente");
                candidature.setNote(0);
                candidature.setDocuments(document);

                // Affichage des infos de la candidature
                System.out.println("Candidature préparée :");
                System.out.println("  ID Annonce     : " + candidature.getIdAnnonce());
                System.out.println("  ID Demandeur   : " + candidature.getIdDemandeur());
                System.out.println("  Date           : " + candidature.getDateCandidature());
                System.out.println("  Statut         : " + candidature.getStatut());
                System.out.println("  Note           : " + candidature.getNote());
                System.out.println("  Documents      : " + candidature.getDocuments());

                CandidatureDAOImpl candidatureDAO = new CandidatureDAOImpl();
                System.out.println("Ajout de la candidature dans la BDD...");
                candidatureDAO.ajouterCandidature(candidature);
                System.out.println("Candidature ajoutée avec succès.");

                JOptionPane.showMessageDialog(this,
                        "Votre candidature a été envoyée avec succès!",
                        "Succès",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this,
                        "Erreur lors de l'envoi de la candidature: " + e.getMessage(),
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                System.out.println("Erreur SQL attrapée : " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("L'utilisateur a annulé la postulation.");
        }

        System.out.println(">>> Fin de la méthode postulerAction()");
    }

    // Méthode pour récupérer l'ID de l'utilisateur depuis la session (à adapter selon ton gestionnaire de session)
    private Integer getUserIdFromSession() {
        // Exemple : on peut utiliser un framework comme HttpSession dans un contexte web
        // Si l'utilisateur est connecté, il doit avoir un attribut "userId" dans la session
        // return session.getAttribute("userId");

        // Ici, on retourne un ID fictif pour simuler l'utilisateur connecté
        Integer userId = 1;  // Remplacer cette valeur par celle issue de ta logique de session
        System.out.println("User ID récupéré depuis la session : " + userId);
        return userId;
    }

    // Méthode fictive pour rediriger vers la page de connexion
    private void redirectToLoginPage() {
        // Cette méthode redirige l'utilisateur vers la page de connexion
        // En réalité, ici tu utiliseras une redirection dans ton application
        // Si tu es sur un environnement web, tu pourrais faire quelque chose comme :
        // response.sendRedirect("loginPage.jsp");
        System.out.println("Redirection vers la page de connexion...");
    }



}
