package Vue;

import DAO.UtilisateurDAO;
import DAO.UtilisateurDAOImpl;
import Modele.DemandeurEmploi;
import Modele.SessionUtilisateur;
import Modele.Utilisateur;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Objects;

public class LoginView extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private UtilisateurDAO dao;
    public SessionUtilisateur session;


    public LoginView(String mode) {
        dao = new UtilisateurDAOImpl();
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        JPanel panelConnexion = createConnexionPanel();
        JPanel panelInscription = createInscriptionPanel();

        mainPanel.add(panelConnexion, "connexion");
        mainPanel.add(panelInscription, "inscription");

        add(mainPanel);
        setTitle("Connexion / Inscription");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        cardLayout.show(mainPanel, mode.toLowerCase());
        setVisible(true);
    }
    private JPanel createConnexionPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 1));
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Se connecter");
        JButton switchToInscription = new JButton("Créer un compte");JButton retourAccueil = new JButton("Retour à l'accueil");

        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            try {
                Utilisateur user = dao.connecter(email, password);

                if (user != null) {
                    // Initialisation de la session
                    session = SessionUtilisateur.getInstance();
                    session.setId(user.getId()); // Vous devrez ajouter getId() à votre classe Utilisateur
                    session.setNom(user.getNom());
                    session.setPrenom(user.getPrenom());
                    session.setEmail(user.getEmail());
                    session.setRole(user.getType()); // "Admin" ou "Demandeur"

                    // Si c'est un demandeur, on ajoute les infos supplémentaires
                    if (user instanceof DemandeurEmploi) {
                        DemandeurEmploi demandeur = (DemandeurEmploi)user;
                        // Ajoutez ici les setters supplémentaires si nécessaire
                    }

                    JOptionPane.showMessageDialog(this, "Bienvenue " + user.getPrenom() + " (" + user.getType() + ")");
                    dispose();

                    // Redirection en fonction du type d'utilisateur
                    if (user.getType().equalsIgnoreCase("Admin")) {
                        new MainPage(); // Ou AdminView si vous en avez une
                    } else {
                        new MainPage(); // Passez l'utilisateur si nécessaire
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Échec de la connexion.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erreur de connexion à la base de données");
                ex.printStackTrace();
            }
        });

        switchToInscription.addActionListener(e -> cardLayout.show(mainPanel, "inscription"));
        retourAccueil.addActionListener(e -> {
            dispose(); // Ferme LoginView
            new MainPage(); // Réouvre la page principale
        });

        panel.add(new JLabel("Email:")); panel.add(emailField);
        panel.add(new JLabel("Mot de passe:")); panel.add(passwordField);
        panel.add(loginButton);
        panel.add(switchToInscription);
        panel.add(retourAccueil);
        return panel;
    }

    private JPanel createInscriptionPanel() {
        JPanel panel = new JPanel(new GridLayout(10, 2));
        JTextField nomField = new JTextField();
        JTextField prenomField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField adresseField = new JTextField();
        JTextField experienceField = new JTextField();
        JTextField cvField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton registerButton = new JButton("S'inscrire");
        JButton switchToConnexion = new JButton("Retour à la connexion");

        JButton retourAccueil = new JButton("Retour à l'accueil");

        registerButton.addActionListener(e -> {
            try {
                Utilisateur user = new Utilisateur(
                        nomField.getText(),
                        prenomField.getText(),
                        Integer.parseInt(ageField.getText()),
                        emailField.getText(),
                        adresseField.getText(),
                        experienceField.getText(),
                        cvField.getText(),
                        new String(passwordField.getPassword()), "Demandeur"
                );
                boolean success = dao.inscrireDemandeur(user);

                if (user != null) {
                    JOptionPane.showMessageDialog(this, "Inscription réussie !");
                    dispose();
                    new MainPage();
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur lors de l'inscription.");
                }

                if (success) cardLayout.show(mainPanel, "connexion");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Âge invalide.");
            }
        });

        switchToConnexion.addActionListener(e -> cardLayout.show(mainPanel, "connexion"));

        retourAccueil.addActionListener(e -> {
            dispose(); // Ferme LoginView
            new MainPage(); // Réouvre la page principale
        });



        panel.add(new JLabel("Nom:")); panel.add(nomField);
        panel.add(new JLabel("Prénom:")); panel.add(prenomField);
        panel.add(new JLabel("Âge:")); panel.add(ageField);
        panel.add(new JLabel("Email:")); panel.add(emailField);
        panel.add(new JLabel("Adresse:")); panel.add(adresseField);
        panel.add(new JLabel("Expérience:")); panel.add(experienceField);
        panel.add(new JLabel("CV (nom de fichier):")); panel.add(cvField);
        panel.add(new JLabel("Mot de passe:")); panel.add(passwordField);
        panel.add(registerButton);
        panel.add(switchToConnexion);
        panel.add(retourAccueil);
        return panel;
    }

}
