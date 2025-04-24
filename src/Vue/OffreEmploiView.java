package Vue;

import DAO.OffreEmploiDAOImpl;
import DAO.CandidatureDAOImpl;
import Modele.Annonce;
import Modele.Candidature;
import Modele.SessionUtilisateur;
import Vue.Components.FooterComponent;
import Vue.Components.HeaderComponent;
import service.Services;

import jakarta.mail.MessagingException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

//création de la page OffreEmploi
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

    //création du bloc central avec toutes les offres
    private JPanel createContentPanel(Color bleuFonce, Color bleuClair) {
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titre = new JLabel("Nos offres d'emploi", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 24));
        titre.setForeground(bleuFonce);
        contentPanel.add(titre, BorderLayout.NORTH);

        initializeTableModel();
        configureTableAppearance(bleuClair);

        //tri par ordre croissant ou décroissant lorsqu'on clique sur le nom de notre colonne
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        annoncesTable.setRowSorter(sorter);

        //comparateur pour le salaire
        sorter.setComparator(2, (s1, s2) -> {
            int val1 = Integer.parseInt(s1.toString().replaceAll("[^\\d]", ""));
            int val2 = Integer.parseInt(s2.toString().replaceAll("[^\\d]", ""));
            return Integer.compare(val1, val2);
        });

        //ajout d'une barre de recherche
        JPanel searchPanel = new JPanel(new BorderLayout(10, 10));
        JLabel searchLabel = new JLabel("🔍 Rechercher :");
        searchLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        JTextField searchField = new JTextField();
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 14));


        //comparer ce que l'utilisateur a tapé par rapport à toutes les informations des offres (toutes les colonnes
        //+ toutes les lignes
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filterTable();
            }
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filterTable();
            }
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filterTable();
            }

            private void filterTable() {
                String text = searchField.getText();
                if (text.trim().isEmpty()) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });

        searchPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(searchPanel, BorderLayout.NORTH);
        centerPanel.add(new JScrollPane(annoncesTable), BorderLayout.CENTER);

        JPanel buttonPanel = createButtonPanel(bleuFonce, bleuClair);

        contentPanel.add(centerPanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        loadAnnonces();

        return contentPanel;
    }

    //initialisation du tableau avec les offres d'emploi
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

    private void configureTableAppearance(Color bleuClair) {
        annoncesTable.setRowHeight(30);
        annoncesTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        annoncesTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        annoncesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        annoncesTable.setIntercellSpacing(new Dimension(0, 0));
        annoncesTable.setShowGrid(false);
        annoncesTable.setSelectionBackground(bleuClair);
        annoncesTable.setSelectionForeground(Color.WHITE);
    }

    //générer différents boutons en fonction du type de l'utilisateur
    private JPanel createButtonPanel(Color bleuFonce, Color bleuClair) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        //vérifier si l'utilisateur est un administrateur
        boolean isAdmin = SessionUtilisateur.getInstance().isAdmin();

        if (isAdmin) {
            JButton gererCandidaturesButton = createStyledButton("Gérer les candidatures", bleuClair, e -> gererCandidaturesAction());
            JButton gererOffresButton = createStyledButton("Gérer les offres", bleuClair, e -> gererOffresAction());
            buttonPanel.add(gererCandidaturesButton);
            buttonPanel.add(gererOffresButton);
        } else {
            JButton postulerButton = createStyledButton("Postuler", bleuClair, e -> postulerAction());
            buttonPanel.add(postulerButton);
        }

        JButton descriptionButton = createStyledButton("Voir Description", Color.DARK_GRAY, e -> afficherDescriptionOffre());
        buttonPanel.add(descriptionButton);

        JButton refreshButton = createStyledButton("Actualiser", bleuFonce, e -> loadAnnonces());
        buttonPanel.add(refreshButton);

        return buttonPanel;
    }

    private void gererCandidaturesAction() {
        navigateTo(new GestionCandidaturesView());
    }

    private void gererOffresAction() {
        navigateTo(new GestionOffresView());
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

    //afficher le détail de la description de l'offre suite à l'action sur un bouton
    private void afficherDescriptionOffre() {
        int selectedRow = annoncesTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez sélectionner une offre pour voir sa description.",
                    "Aucune sélection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String titre = (String) tableModel.getValueAt(selectedRow, 0);

        try {
            OffreEmploiDAOImpl dao = new OffreEmploiDAOImpl();
            Annonce annonce = dao.getAnnonceByTitre(titre);

            if (annonce != null) {
                String description = annonce.getDescription();
                JTextArea textArea = new JTextArea(description);
                textArea.setWrapStyleWord(true);
                textArea.setLineWrap(true);
                textArea.setEditable(false);
                textArea.setBackground(new Color(245, 245, 245));
                textArea.setFont(new Font("SansSerif", Font.PLAIN, 14));

                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(500, 300));

                //ouverture d'une fenêtre pop-up
                JOptionPane.showMessageDialog(this, scrollPane, "Description de l'offre", JOptionPane.INFORMATION_MESSAGE);
            } else JOptionPane.showMessageDialog(this,
                    "Offre introuvable.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Erreur lors de la récupération de l'offre: " + e.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void postulerAction() {
        //vérification de la connexion de l'utilisateur
        if (SessionUtilisateur.getInstance().getEmail() == null) {
            JOptionPane.showMessageDialog(this,
                    "Vous devez être connecté pour postuler. Redirection vers la page de connexion.",
                    "Non connecté",
                    JOptionPane.WARNING_MESSAGE);
            //redirection sur la page de connexion
            redirectToLoginPage();
            return;
        }

        int selectedRow = annoncesTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez sélectionner une offre à laquelle postuler",
                    "Aucune sélection",
                    JOptionPane.WARNING_MESSAGE);
            // send email function

            return;
        }

        String titreOffre = (String) tableModel.getValueAt(selectedRow, 0);

        int option = JOptionPane.showConfirmDialog(this,
                "Voulez-vous postuler à l'offre : " + titreOffre + "?",
                "Confirmation de postulation",
                JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            try {
                OffreEmploiDAOImpl offreDAO = new OffreEmploiDAOImpl();
                Annonce annonce = offreDAO.getAnnonceByTitre(titreOffre);

                if (annonce == null) {
                    JOptionPane.showMessageDialog(this,
                            "Annonce introuvable.",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int idDemandeur = SessionUtilisateur.getInstance().getId();
                int idAnnonce = annonce.getId();

                // Ajout de la candidature
                CandidatureDAOImpl candidatureDAO = new CandidatureDAOImpl();

                boolean existeDeja = candidatureDAO.existeCandidature(idDemandeur, idAnnonce);

                if (existeDeja){
                    JOptionPane.showMessageDialog(this,
                            "Vous avez déjà postulé à cette offre!",
                            "Candidature existante",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Création de la candidature
                Candidature candidature = new Candidature();
                candidature.setIdAnnonce(annonce.getId());
                candidature.setIdDemandeur(SessionUtilisateur.getInstance().getId());
                candidature.setDateCandidature(new java.sql.Date(System.currentTimeMillis()));
                candidature.setStatut("En attente");
                candidature.setNote(0);
                candidature.setDocuments("lettre_motivation_" + SessionUtilisateur.getInstance().getId() + ".pdf");

                candidatureDAO.ajouterCandidature(candidature);

                try {
                    String to = SessionUtilisateur.getInstance().getEmail();
                    String subject = "Candidature enregistrée - MatchaJob";
                    String body = """
                                    <h2>Bonjour %s,</h2>
                                    <p>Votre candidature à <strong>%s</strong> a été enregistrée.</p>
                                    <p>Nous vous contacterons si votre profil correspond.</p>
                                    <p>Merci de votre confiance.</p>
                                  """.formatted(SessionUtilisateur.getInstance().getPrenom(), annonce.getTitre()
                    );

                    Services.EMAIL.send(to, subject, body);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }

                JOptionPane.showMessageDialog(this,
                        "Votre candidature a été envoyée avec succès!",
                        "Succès",
                        JOptionPane.INFORMATION_MESSAGE);


            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this,
                        "Erreur lors de la postulation: " + e.getMessage(),
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    private void redirectToLoginPage() {
        navigateTo(new LoginView("mode"));
    }

    private void navigateTo(JFrame newFrame) {
        dispose(); // Ferme la fenêtre actuelle
        newFrame.setLocationRelativeTo(null);
        newFrame.setVisible(true);
    }
}
