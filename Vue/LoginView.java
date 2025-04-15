package Vue;

import DAO.UtilisateurDAOImpl;
import Modele.SessionUtilisateur;
import Modele.Utilisateur;
import Vue.Components.FooterComponent;
import Vue.Components.HeaderComponent;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class LoginView extends JFrame {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel mainPanel = new JPanel(cardLayout);
    private final UtilisateurDAOImpl utilisateurDAO = new UtilisateurDAOImpl();

    public LoginView(String mode) {
        configureFrame();
        initUI(mode);
    }

    private void configureFrame() {
        setTitle("MatchaJob - Connexion");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initUI(String mode) {
        JPanel container = new JPanel(new BorderLayout());

        // Header
        container.add(new HeaderComponent(this), BorderLayout.NORTH);

        // Contenu principal avec padding
        JPanel contentContainer = new JPanel(new BorderLayout());
        contentContainer.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100)); // Grands marges
        contentContainer.setBackground(Color.WHITE);

        mainPanel.add(createLoginPanel(), "connexion");
        mainPanel.add(createRegisterPanel(), "inscription");
        contentContainer.add(mainPanel, BorderLayout.CENTER);

        container.add(contentContainer, BorderLayout.CENTER);

        // Footer
        container.add(new FooterComponent(), BorderLayout.SOUTH);

        add(container);
        cardLayout.show(mainPanel, mode.toLowerCase());
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espacement augmenté
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Style
        Color bleuFonce = new Color(9, 18, 66);
        Color bleuClair = new Color(45, 132, 255);

        // Titre
        JLabel titleLabel = new JLabel("CONNEXION", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 32)); // Taille augmentée
        titleLabel.setForeground(bleuFonce);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        // Champs de formulaire
        JTextField emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(300, 40)); // Taille augmentée
        emailField.setFont(new Font("SansSerif", Font.PLAIN, 18));

        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(300, 40));
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 18));

        // Boutons
        JButton loginButton = createStyledButton("SE CONNECTER", bleuClair, 200, 40);
        JButton switchButton = createStyledButton("CRÉER UN COMPTE", new Color(180, 180, 180), 200, 40);
        JButton homeButton = createStyledButton("RETOUR À L'ACCUEIL", bleuFonce, 200, 40);

        // Positionnement
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(createFormLabel("Email:"), gbc);

        gbc.gridx = 1;
        //gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        panel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(createFormLabel("Mot de passe:"), gbc);

        gbc.gridx = 1;
        //gbc.gridy = 2;
        panel.add(passwordField, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, gbc);

        gbc.gridy = 4;
        panel.add(switchButton, gbc);

        gbc.gridy = 5;
        panel.add(homeButton, gbc);

        // Listeners
        loginButton.addActionListener(e -> handleLogin(
                emailField.getText(),
                new String(passwordField.getPassword())
        ));

        switchButton.addActionListener(e -> cardLayout.show(mainPanel, "inscription"));
        homeButton.addActionListener(e -> returnToHome());

        return panel;
    }

    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Style
        Color bleuFonce = new Color(9, 18, 66);
        Color bleuClair = new Color(45, 132, 255);

        // Titre
        JLabel titleLabel = new JLabel("INSCRIPTION DEMANDEUR", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        titleLabel.setForeground(bleuFonce);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        // Champs de formulaire
        JTextField[] fields = new JTextField[7];
        for (int i = 0; i < fields.length; i++) {
            fields[i] = new JTextField();
            fields[i].setPreferredSize(new Dimension(300, 40));
            fields[i].setFont(new Font("SansSerif", Font.PLAIN, 18));
        }

        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(300, 40));
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 18));

        // Boutons
        JButton registerButton = createStyledButton("S'INSCRIRE", bleuClair, 200, 40);
        JButton switchButton = createStyledButton("DÉJÀ UN COMPTE ?", new Color(180, 180, 180), 200, 40);
        JButton homeButton = createStyledButton("RETOUR À L'ACCUEIL", bleuFonce, 200, 40);

        // Positionnement
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(titleLabel, gbc);

        String[] labels = {"Nom:", "Prénom:", "Âge:", "Email:", "Adresse:", "Expérience:", "CV:", "Mot de passe:"};

        for (int i = 0; i < labels.length; i++) {
            gbc.gridwidth = 1;
            gbc.gridx = 0;
            gbc.gridy = i + 1;
            panel.add(createFormLabel(labels[i]), gbc);

            gbc.gridx = 1;
            if (i == labels.length - 1) {
                panel.add(passwordField, gbc);
            } else {
                panel.add(fields[i], gbc);
            }
        }

        gbc.gridwidth = 10;
        gbc.gridx = 0;
        gbc.gridy = labels.length + 1;
        panel.add(registerButton, gbc);

        gbc.gridy = labels.length + 2;
        panel.add(switchButton, gbc);

        gbc.gridy = labels.length + 3;
        panel.add(homeButton, gbc);

        // Listeners
        registerButton.addActionListener(e -> handleRegistration(fields, passwordField));
        switchButton.addActionListener(e -> cardLayout.show(mainPanel, "connexion"));
        homeButton.addActionListener(e -> returnToHome());

        return panel;
    }

    private JLabel createFormLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 18));
        return label;
    }

    private JButton createStyledButton(String text, Color bgColor, int width, int height) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(width, height));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }

    private void handleLogin(String email, String password) {
        try {
            Utilisateur user = utilisateurDAO.connecter(email, password);

            if (user != null) {
                initUserSession(user);
                redirectAfterLogin(user);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Identifiants incorrects",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            showDatabaseError();
        }
    }

    private void initUserSession(Utilisateur user) {
        SessionUtilisateur session = SessionUtilisateur.getInstance();
        session.setId(user.getId());
        session.setNom(user.getNom());
        session.setPrenom(user.getPrenom());
        session.setEmail(user.getEmail());
        session.setRole(user.getType());
    }

    private void redirectAfterLogin(Utilisateur user) {
        dispose();
        new MainPage();
    }

    private void handleRegistration(JTextField[] fields, JPasswordField passwordField) {
        try {
            Utilisateur newUser = new Utilisateur(
                    fields[0].getText(),
                    fields[1].getText(),
                    Integer.parseInt(fields[2].getText()),
                    fields[3].getText(),
                    fields[4].getText(),
                    fields[5].getText(),
                    fields[6].getText(),
                    new String(passwordField.getPassword()),
                    "Demandeur"
            );

            if (utilisateurDAO.inscrireDemandeur(newUser)) {
                JOptionPane.showMessageDialog(this, "Inscription réussie !");
                cardLayout.show(mainPanel, "connexion");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "L'âge doit être un nombre valide",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void returnToHome() {
        dispose();
        new MainPage().setVisible(true);
    }

    private void showDatabaseError() {
        JOptionPane.showMessageDialog(this,
                "Erreur de connexion à la base de données",
                "Erreur",
                JOptionPane.ERROR_MESSAGE);
    }
}