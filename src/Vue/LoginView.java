package Vue;

import DAO.UtilisateurDAOImpl;
import Modele.SessionUtilisateur;
import Modele.Utilisateur;
import Vue.Components.FooterComponent;
import Vue.Components.HeaderComponent;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;

//Ici, page pour la connexion + inscription
public class LoginView extends JFrame {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel mainPanel = new JPanel(cardLayout);
    private final UtilisateurDAOImpl utilisateurDAO = new UtilisateurDAOImpl();
    private File cvFichier = null;

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

        //ajout du header
        container.add(new HeaderComponent(this), BorderLayout.NORTH);

        JPanel contentContainer = new JPanel(new BorderLayout());
        contentContainer.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
        contentContainer.setBackground(Color.WHITE);

        //bandeau déroulant, avec les termes connexion et inscription qui renvoient vers leur fonction respective
        mainPanel.add(createLoginPanel(), "connexion");
        mainPanel.add(createRegisterPanel(), "inscription");
        contentContainer.add(mainPanel, BorderLayout.CENTER);

        container.add(contentContainer, BorderLayout.CENTER);

        //ajout du footer
        container.add(new FooterComponent(), BorderLayout.SOUTH);

        add(container);
        cardLayout.show(mainPanel, mode.toLowerCase());
    }

    //page de connexion
    private JPanel createLoginPanel() {
        //mise en page de la page connexion
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        //style de notre page
        Color bleuFonce = new Color(9, 18, 66);
        Color bleuClair = new Color(45, 132, 255);

        //style autour du titre
        JLabel titleLabel = new JLabel("CONNEXION", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        titleLabel.setForeground(bleuFonce);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        //création des champs du formulaire : une case email + une case mot de passe
        JTextField emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(300, 40));
        emailField.setFont(new Font("SansSerif", Font.PLAIN, 18));

        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(300, 40));
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 18));

        //création des trois boutons : se connecter / créer un compte / retour
        JButton loginButton = createStyledButton("SE CONNECTER", bleuClair, 200, 40);
        JButton switchButton = createStyledButton("CRÉER UN COMPTE", new Color(180, 180, 180), 200, 40);
        JButton homeButton = createStyledButton("RETOUR À L'ACCUEIL", bleuFonce, 200, 40);

        //positionnement de nos "briques"
        //pour le titre:
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(titleLabel, gbc);

        //pour l'email
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        //case affichage texte:
        panel.add(createFormLabel("Email:"), gbc);

        //case pour remplir:
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        panel.add(emailField, gbc);

        //case affichage texte:
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(createFormLabel("Mot de passe:"), gbc);

        //case pour remplir:
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        //affichage boutons
        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, gbc);

        gbc.gridy = 4;
        panel.add(switchButton, gbc);

        gbc.gridy = 5;
        panel.add(homeButton, gbc);


        //action des boutons + redirection (page d'accueil ou inscription ou page connecté)
        loginButton.addActionListener(e -> handleLogin(
                emailField.getText(),
                new String(passwordField.getPassword())
        ));

        switchButton.addActionListener(e -> cardLayout.show(mainPanel, "inscription"));
        homeButton.addActionListener(e -> returnToHome());

        return panel;
    }

    //création de la page d'inscription
    private JScrollPane createRegisterPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //style de la page
        Color bleuFonce = new Color(9, 18, 66);
        Color bleuClair = new Color(45, 132, 255);

        //titre
        JLabel titleLabel = new JLabel("INSCRIPTION DEMANDEUR", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        titleLabel.setForeground(bleuFonce);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        //création des champs pour la page d'inscription
        JTextField[] fields = new JTextField[7];
        for (int i = 0; i < fields.length; i++) {
            fields[i] = new JTextField();
            fields[i].setPreferredSize(new Dimension(300, 40));
            fields[i].setFont(new Font("SansSerif", Font.PLAIN, 18));
        }

        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(300, 40));
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 18));

        //différents boutons
        JButton registerButton = createStyledButton("S'INSCRIRE", bleuClair, 200, 40);
        JButton switchButton = createStyledButton("DÉJÀ UN COMPTE ?", new Color(180, 180, 180), 200, 40);
        JButton homeButton = createStyledButton("RETOUR À L'ACCUEIL", bleuFonce, 200, 40);

        //positionnement des cases
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
            if (i == 6) {
                JPanel filePanel = new JPanel(new BorderLayout(5, 5));
                JButton browseBtn = new JButton("Choisir un fichier...");
                JLabel fileLabel = new JLabel("Aucun fichier");

                browseBtn.addActionListener(e -> {
                    JFileChooser chooser = new JFileChooser();
                    int res = chooser.showOpenDialog(this);
                    if (res == JFileChooser.APPROVE_OPTION) {
                        cvFichier = chooser.getSelectedFile();
                        fileLabel.setText(cvFichier.getName());
                    }
                });

                filePanel.add(browseBtn, BorderLayout.WEST);
                filePanel.add(fileLabel, BorderLayout.CENTER);
                panel.add(filePanel, gbc);
            } else if (i == 7) {
                panel.add(passwordField, gbc);
            } else {
                panel.add(fields[i], gbc);
            }
            /*
            if (i == labels.length - 1) {
                panel.add(passwordField, gbc);
            } else {
                panel.add(fields[i], gbc);
            }

             */
        }

        //positionnement des boutons
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = labels.length + 1;
        panel.add(registerButton, gbc);

        gbc.gridy = labels.length + 2;
        panel.add(switchButton, gbc);

        gbc.gridy = labels.length + 3;
        panel.add(homeButton, gbc);

        //redirection + action des boutons
        registerButton.addActionListener(e -> handleRegistration(fields, passwordField));
        switchButton.addActionListener(e -> cardLayout.show(mainPanel, "connexion"));
        homeButton.addActionListener(e -> returnToHome());


        //ajout d'une barre afin de "scroller" sur la page d'inscription
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setBackground(Color.WHITE);
        verticalScrollBar.setForeground(bleuClair);
        verticalScrollBar.setUnitIncrement(16);
        verticalScrollBar.setPreferredSize(new Dimension(10, 0));

        return scrollPane;
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

    //vérification dans la bdd que email + mot de passe sont corrects
    //si oui, redirection
    //si non, messsage d'erreur
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
        session.setAdresse(user.getAdresse());
        session.setAge(user.getAge());
        session.setExperience(user.getExperience());
        session.setCv(user.getCv());
        session.setRole(user.getType());
        System.out.println("CV récupéré depuis la BDD: " + user.getCv());
    }

    //redirection vers MainPage(), après la vérif de la connexion
    private void redirectAfterLogin(Utilisateur user) {
        dispose();
        new MainPage();
    }

    //ajout des champs de l'inscription dans la bdd
    private void handleRegistration(JTextField[] fields, JPasswordField passwordField) {
        try {
            //vérification que tous les champs soient remplis
            String[] labels = {"Nom", "Prénom", "Âge", "Email", "Adresse", "Expérience", "CV", "Mot de passe"};
            for (int i = 0; i < 6; i++) {
                if (fields[i].getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Veuillez remplir le champ : " + labels[i],
                            "Champ manquant",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }


            String nom = fields[0].getText().trim();
            String prenom = fields[1].getText().trim();
            String email = fields[3].getText().trim();

            //condition pour que les cases nom et prénom ne contiennent que des lettres
            if (!nom.matches("[a-zA-ZÀ-ÿ\\s]+")) {
                JOptionPane.showMessageDialog(this,
                        "Le nom ne doit contenir que des lettres.",
                        "Nom invalide",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!prenom.matches("[a-zA-ZÀ-ÿ\\s]+")) {
                JOptionPane.showMessageDialog(this,
                        "Le prénom ne doit contenir que des lettres.",
                        "Prénom invalide",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            //condition par rapport au format de l'adresse mail
            //https://www.geeksforgeeks.org/check-email-address-valid-not-java/
            //https://howtodoinjava.com/java/regex/java-regex-validate-email-address/
            if (!email.contains("@") || !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                JOptionPane.showMessageDialog(this,
                        "Adresse email invalide.",
                        "Email invalide",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            //vérification que l'adresse mail n'a pas été utilisée
            if (utilisateurDAO.utilisateurExiste(email)) {
                JOptionPane.showMessageDialog(this,
                        "Cet email est déjà utilisé. Veuillez en choisir un autre.",
                        "Email déjà utilisé",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }


            //condition de l'âge > 0 + nb entier
            int age;
            try {
                age = Integer.parseInt(fields[2].getText().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "L'âge doit être un nombre entier valide.",
                        "Âge invalide",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }


            String cvPath = "cv_default.pdf";
            if (cvFichier != null) {
                try {
                    String destDir = "assets/cv/";
                    new File(destDir).mkdirs();
                    String filename = cvFichier.getName();
                    Path destPath = Paths.get(destDir + filename);
                    Files.copy(cvFichier.toPath(), destPath, StandardCopyOption.REPLACE_EXISTING);
                    cvPath = destPath.toString();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Erreur lors de l'enregistrement du fichier CV.");
                    return;
                }
            }else{
                JOptionPane.showMessageDialog(this,
                        "Veuillez choisir un fichier CV.",
                        "CV manquant",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (passwordField.getPassword().length == 0) {
                JOptionPane.showMessageDialog(this,
                        "Veuillez entrer un mot de passe.",
                        "Champ manquant",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            Utilisateur newUser = new Utilisateur(
                    nom,
                    prenom,
                    age,
                    email,
                    fields[4].getText(),
                    fields[5].getText(),
                    cvPath,
                    new String(passwordField.getPassword()),
                    "Demandeur"
            );

            //message si inscription réussie + redirection vers connexion
            if (utilisateurDAO.inscrireDemandeur(newUser)) {
                JOptionPane.showMessageDialog(this, "Inscription réussie !");
                cardLayout.show(mainPanel, "connexion");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Une erreur est survenue lors de l'inscription : " + e.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
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